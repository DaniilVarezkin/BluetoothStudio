package com.daniilvarezhkin.bluetoothstudio.data.ble.connection

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import com.daniilvarezhkin.bluetoothstudio.data.log.gattError
import com.daniilvarezhkin.bluetoothstudio.data.log.gattSuccess
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.GattConnection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.DeviceConnection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.ConnectionState
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.command.Command
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.command.CommandError
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.ServiceKey
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogTag
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogRepository
import com.daniilvarezhkin.core.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber


@SuppressLint("MissingPermission")
class DeviceConnectionImpl(
    private val context: Context,
    private val device: BluetoothDevice,
    private val gattMapper: GattMapper,
    private val logRepository: LogRepository,
) : DeviceConnection {

    override val address: String
        get() = device.address
    override val deviceName: String?
        get() = device.name


    private var bluetoothGatt: BluetoothGatt? = null
    private var gattConnection: GattConnection? = null

    private val deviceGattRepository = DeviceGattRepositoryImpl(
        services = MutableStateFlow(emptyMap()),
        characteristics = MutableStateFlow(emptyMap()),
        charValues = MutableStateFlow(emptyMap())
    )

    private val _connectionState: MutableStateFlow<ConnectionState> = MutableStateFlow(ConnectionState.DISCONNECTED)

    override val connectionState = _connectionState.asStateFlow()

    override val services: StateFlow<Map<ServiceKey, GattService>>
        get() = deviceGattRepository.getServiceFlow()

    override val characteristics: StateFlow<Map<CharacteristicKey, GattCharacteristic>>
        get() = deviceGattRepository.getCharacteristicsFlow()

    override val charValues: StateFlow<Map<CharacteristicKey, ByteArray?>>
        get() = deviceGattRepository.getCharValuesFlow()


    private val gattCallback = object : BluetoothGattCallback() {

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {

            if (status != BluetoothGatt.GATT_SUCCESS) {
                _connectionState.update { ConnectionState.DISCONNECTED }
                gatt?.close()

                val errorMsg = "GATT Error: ${status.toGattStatusString()} ($status)"
                Timber.e("[$address] $errorMsg")
                logRepository.gattError(address, deviceName, LogTag.CONNECT, errorMsg)
                return
            }

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                logRepository.gattSuccess(address, deviceName, LogTag.CONNECT, "Connected. Discovering services...")

                gatt?.discoverServices()
                _connectionState.update { ConnectionState.DISCOVER_SERVICES }
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                _connectionState.update { ConnectionState.DISCONNECTED }

                gatt?.close()
                if (gatt == bluetoothGatt) {
                    bluetoothGatt = null
                }

                logRepository.gattSuccess(address, deviceName, LogTag.CONNECT, "Disconnected from device")
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            if (status != BluetoothGatt.GATT_SUCCESS) {
                Timber.e("Error discovering services. Status: $status.")
                logRepository.gattError(
                    address,
                    deviceName,
                    LogTag.DISCOVER,
                    "Error discovering services. GATT Error: ${status.toGattStatusString()} ($status)"
                )
                return
            }

            gatt?.let { gatt ->
                val snapshot = gattMapper.mapRawServices(gatt.device.address, gatt.services)
                deviceGattRepository.updateServices(snapshot.services)
                deviceGattRepository.updateCharacteristics(snapshot.characteristics)

                gatt.services.forEach { service ->
                    service.characteristics.forEach { char ->
                        val key = char.createKey(gatt.device.address)
                        deviceGattRepository.addGattObjectIndex(key, char)
                    }
                }

                _connectionState.update { ConnectionState.CONNECTED }

                gattConnection = GattConnectionImpl(
                    gatt = gatt,
                    deviceGattRepository = deviceGattRepository,
                    logRepository = logRepository
                )

                Timber.d("Services discovered")
                logRepository.gattSuccess(address, deviceName,LogTag.DISCOVER, "Services discovered")

            } ?: throw RuntimeException("bluetoothGatt is null")
        }

        @Deprecated("Deprecated in Java")
        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            val key = characteristic.createKey(gatt.device.address)
            gattConnection?.onReadCharacteristic(key, characteristic.value, status)
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
            status: Int
        ) {
            val key = characteristic.createKey(gatt.device.address)
            gattConnection?.onReadCharacteristic(key, value, status)
        }

        override fun onCharacteristicWrite(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            val key = characteristic.createKey(gatt.device.address)
            gattConnection?.onWriteCharacteristic(key, status)
        }
    }

    override fun connect() {
        logRepository.addBLog(
            priority = Log.DEBUG,
            tag = LogTag.CONNECTION,
            message = "Connecting to $address...",
            address = address,
            deviceName = deviceName
        )

        if (_connectionState.value != ConnectionState.DISCONNECTED) {
            logRepository.addBLog(
                priority = Log.WARN,
                tag = LogTag.CONNECTION,
                message = "The device $address is already in the connection status",
                address = address,
                deviceName = deviceName
            )
            return
        }

        _connectionState.update { ConnectionState.CONNECTING }
        bluetoothGatt?.close()
        bluetoothGatt = device.connectGatt(context, false, gattCallback)
    }

    override fun disconnect() {
        logRepository.addBLog(
            priority = Log.DEBUG,
            tag = LogTag.DISCONNECT,
            message = "Disconnect $address...",
            address = address,
            deviceName = deviceName
        )

        bluetoothGatt?.disconnect()
        bluetoothGatt?.close()
        bluetoothGatt = null
    }

    override fun sendCommand(command: Command) {
        Timber.d("Получена команда: ${command.javaClass.simpleName}")
        if (gattConnection == null) {
            command.onGetResult(Result.Error(CommandError.NoConnection))
        } else {
            gattConnection?.sendCommand(command)
        }
    }
}