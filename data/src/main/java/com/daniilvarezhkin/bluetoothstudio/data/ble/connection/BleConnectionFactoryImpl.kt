package com.daniilvarezhkin.bluetoothstudio.data.ble.connection

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.BleConnection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.BleConnectionFactory
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.ConnectionError
import com.daniilvarezhkin.core.Result

class BleConnectionFactoryImpl(
    private val context: Context,
    private val gattMapper: GattMapper
) : BleConnectionFactory {

    private val bluetoothAdapter: BluetoothAdapter?
        get() = (context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager)?.adapter

    override fun createConnection(macAddress: String): Result<BleConnection, ConnectionError> {
        try {
            val adapter = bluetoothAdapter ?: return Result.Error(ConnectionError.BluetoothIsNotSupported)

            if (!adapter.isEnabled) {
                return Result.Error(ConnectionError.BluetoothIsNotEnabled)
            }

            if (!BluetoothAdapter.checkBluetoothAddress(macAddress)) {
                return Result.Error(ConnectionError.InvalidMacAddress)
            }
            val device = adapter.getRemoteDevice(macAddress)
            return Result.Success(BleConnectionImpl(context.applicationContext, device, gattMapper))
        } catch (e: Throwable) {
            return Result.Error(ConnectionError.UnknownError(e))
        }
    }
}