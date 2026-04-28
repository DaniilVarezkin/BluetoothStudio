package com.daniilvarezhkin.bluetoothstudio.data.ble.connection

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothStatusCodes
import android.os.Build
import com.daniilvarezhkin.bluetoothstudio.data.log.charError
import com.daniilvarezhkin.bluetoothstudio.data.log.charNotFound
import com.daniilvarezhkin.bluetoothstudio.data.log.charSuccess
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.GattConnection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.WriteType
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.command.Command
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.command.CommandError
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.command.CommandResult
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogTag
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogRepository
import com.daniilvarezhkin.core.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber


@Suppress("DEPRECATION")
@SuppressLint("MissingPermission")
class GattConnectionImpl(
    private val gatt: BluetoothGatt,
    private val deviceGattRepository: DeviceGattRepositoryImpl,
    private val logRepository: LogRepository,
) : GattConnection {

    private val connectionScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val commandChannel = Channel<Command>(capacity = Channel.BUFFERED)
    private val commandResultChannel =
        Channel<Result<CommandResult, CommandError>>(capacity = 1, onBufferOverflow = BufferOverflow.SUSPEND)

    private val commandChannelReceiverJob = commandChannel
        .receiveAsFlow()
        .onEach { command ->
            val result = handleCommand(command)
            command.onGetResult(result)
        }.launchIn(connectionScope)


    private fun readCharacteristic(key: CharacteristicKey): Boolean {
        val gattChar = deviceGattRepository.getRawChar(key)
        if (gattChar == null) {
            Timber.w("The characteristic was not found in the key: $key")
            logRepository.charNotFound(key,gatt.device.name, LogTag.READ)
            return false
        }

        return gatt.readCharacteristic(gattChar)
    }

    private fun writeCharacteristic(key: CharacteristicKey, value: ByteArray, writeType: WriteType): Boolean {
        val gattChar = deviceGattRepository.getRawChar(key)
        if (gattChar == null) {
            Timber.w("The characteristic was not found in the key: $key")
            logRepository.charNotFound(key, gatt.device.name,LogTag.WRITE)
            return false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val result = gatt.writeCharacteristic(gattChar, value, writeType.code)
            return result == BluetoothStatusCodes.SUCCESS
        } else {
            return gatt.writeCharacteristic(gattChar)
        }
    }

    private suspend fun executeAndAwait(
        action: () -> Boolean,
    ): Result<CommandResult, CommandError> {
        while (commandResultChannel.tryReceive().isSuccess) {
        }

        return if (action()) {
            withTimeoutOrNull(2000) {
                commandResultChannel.receive()
            } ?: Result.Error(CommandError.Timeout)
        } else {
            Result.Error(CommandError.NotSuccess)
        }
    }

    private suspend fun handleCommand(command: Command) = when (command) {
        is Command.Read -> executeAndAwait {
            readCharacteristic(command.charKey)
        }

        is Command.Write -> executeAndAwait {
            writeCharacteristic(command.charKey, command.value, command.writeType)
        }
    }

    override fun onReadCharacteristic(charKey: CharacteristicKey, value: ByteArray, status: Int) {
        connectionScope.launch {
            if (status != BluetoothGatt.GATT_SUCCESS) {
                commandResultChannel.send(Result.Error(CommandError.NotSuccess))
                logRepository.charError(charKey, gatt.device.name, LogTag.READ, "Read error: $status")
            } else {
                commandResultChannel.send(Result.Success(CommandResult.Read(value)))
                deviceGattRepository.updateCharValue(charKey, value)
                logRepository.charSuccess(charKey, gatt.device.name,LogTag.READ, "Read success", value)
            }
        }
    }

    override fun onWriteCharacteristic(charKey: CharacteristicKey, status: Int) {
        connectionScope.launch {
            if (status != BluetoothGatt.GATT_SUCCESS) {
                commandResultChannel.send(Result.Error(CommandError.NotSuccess))
                logRepository.charError(charKey, gatt.device.name,LogTag.WRITE, "Write error: $status")
            } else {
                commandResultChannel.send(Result.Success(CommandResult.Write))
                logRepository.charSuccess(charKey, gatt.device.name,LogTag.WRITE, "Write success")
            }
        }
    }

    override fun sendCommand(command: Command) {
        connectionScope.launch {
            commandChannel.send(command)
        }
    }
}