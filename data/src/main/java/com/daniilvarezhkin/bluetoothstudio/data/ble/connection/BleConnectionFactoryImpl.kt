package com.daniilvarezhkin.bluetoothstudio.data.ble.connection

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.BleConnectionFactory
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.ConnectionError
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.DeviceConnection
import com.daniilvarezhkin.bluetoothstudio.domain.eventbus.EventBus
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogRepository
import com.daniilvarezhkin.core.Result

/**
 * Фабрика создания подключений к BLE устройствам
 */
class BleConnectionFactoryImpl(
    private val context: Context,
    private val gattMapper: GattMapper,
    private val logRepository: LogRepository,
    private val eventBus: EventBus,
) : BleConnectionFactory {

    private val bluetoothAdapter: BluetoothAdapter?
        get() = (context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager)?.adapter

    override fun createConnection(address: String): Result<DeviceConnection, ConnectionError> {
        try {
            val adapter = bluetoothAdapter ?: return Result.Error(ConnectionError.BluetoothIsNotSupported)

            if (!adapter.isEnabled) {
                return Result.Error(ConnectionError.BluetoothIsNotEnabled)
            }

            if (!BluetoothAdapter.checkBluetoothAddress(address)) {
                return Result.Error(ConnectionError.InvalidMacAddress)
            }
            val device = adapter.getRemoteDevice(address)
            return Result.Success(
                DeviceConnectionImpl(
                    context = context.applicationContext,
                    device = device,
                    gattMapper = gattMapper,
                    logRepository = logRepository,
                    eventBus = eventBus
                )
            )
        } catch (e: Throwable) {
            return Result.Error(ConnectionError.UnknownError(e))
        }
    }
}