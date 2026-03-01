package com.daniilvarezhkin.bluetoothstudio.data.ble.connection

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothProfile
import android.content.Context
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.BleConnection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.ConnectionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber


@SuppressLint("MissingPermission")
class BleConnectionImpl(
    private val context: Context,
    private val device: BluetoothDevice
) : BleConnection {
    private var bluetoothGatt: BluetoothGatt? = null;

    private val _connectionState: MutableStateFlow<ConnectionState> = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState = _connectionState.asStateFlow()

    private val gattCallback = object : BluetoothGattCallback() {

        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {

            if (status != BluetoothGatt.GATT_SUCCESS) {
                Timber.e("Ошибка GATT: $status.")
                _connectionState.value = ConnectionState.DISCONNECTED
                gatt?.close()
                return
            }

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                gatt?.discoverServices()
                _connectionState.value = ConnectionState.DISCOVER_SERVICES
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                _connectionState.value = ConnectionState.DISCONNECTED

                gatt?.close()
                if (gatt == bluetoothGatt) {
                    bluetoothGatt = null
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            if (status != BluetoothGatt.GATT_SUCCESS) {
                Timber.e("Ошибка чтения сервисов: $status.")
                return
            }
            _connectionState.value = ConnectionState.CONNECTED
            Timber.d("Сервисы обнаружены")
        }
    }

    override fun connect() {
        disconnect()
        Timber.e("Подключение к устройству")
        bluetoothGatt = device.connectGatt(context, false, gattCallback)
    }

    override fun disconnect() {
        Timber.e("Отключение от устройства")
        bluetoothGatt?.let {
            it.disconnect()
            it.close()
            bluetoothGatt = null
        }
    }
}