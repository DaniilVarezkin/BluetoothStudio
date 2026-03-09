package com.daniilvarezhkin.bluetoothstudio.data.ble.connection

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothProfile
import android.content.Context
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.BleConnection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.ConnectionState
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber


@SuppressLint("MissingPermission")
class BleConnectionImpl(
    private val context: Context,
    private val device: BluetoothDevice,
    private val gattMapper: GattMapper
) : BleConnection {
    private var bluetoothGatt: BluetoothGatt? = null;

    private val _connectionState: MutableStateFlow<ConnectionState> = MutableStateFlow(ConnectionState.DISCONNECTED)
    override val connectionState = _connectionState.asStateFlow()

    private val _gattServices :MutableStateFlow<List<GattService>> = MutableStateFlow(emptyList())
    override val gattServices = _gattServices.asStateFlow()

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
            Timber.d("Сервисы обнаружены")
            _connectionState.value = ConnectionState.CONNECTED



            gatt?.let { gatt ->
                _gattServices.value = gatt.services.map {
                    gattMapper.run { it.toGattService() }
                }
            } ?: throw RuntimeException("bluetoothGatt is null")
        }

        @Deprecated("Deprecated in Java")
        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                val value = characteristic.value // Берем значение из самого объекта
                processRead(characteristic, value)
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
            status: Int
        ) {
            if (status != BluetoothGatt.GATT_SUCCESS) {
                Timber.e("Ошибка чтения характеристики ${characteristic.uuid}: $status")
                return
            }
            processRead(characteristic, value)
        }

        private fun processRead(
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
        ) {
            Timber.i("Прочитана характеристика UUID: ${characteristic.uuid} Value: ${value.decodeToString()}")

            gattServices.value.forEach { service ->
                if (service.uuid == characteristic.service.uuid) {
                    service.characteristics.forEach { ch ->
                        if (ch.uuid == characteristic.uuid) {
                            ch.value.update { value }
                        }
                    }
                }
            }
        }
    }

    override fun readCharacteristic(characteristic: GattCharacteristic) {
        Timber.i("Чтение характеристики UUID: ${characteristic.uuid}")

        val gatt = bluetoothGatt
        if (gatt == null) {
            Timber.e("bluetoothGatt is null")
            return
        }

        // Находим характеристику
        val gattCharacteristic = gatt.services
            .flatMap { it.characteristics }
            .find {
                it.service.uuid == characteristic.serviceUuid &&
                        it.uuid == characteristic.uuid
            }

        if (gattCharacteristic == null) {
            Timber.e("Characteristic not found: service=${characteristic.serviceUuid}, char=${characteristic.uuid}")
            return
        }

        // Проверяем, поддерживает ли чтение
        if (gattCharacteristic.properties and BluetoothGattCharacteristic.PROPERTY_READ == 0) {
            Timber.e("Characteristic ${characteristic.uuid} does not support READ")
            return
        }

        // Пытаемся прочитать и ПРОВЕРЯЕМ результат
        val success = gatt.readCharacteristic(gattCharacteristic)

        if (success) {
            Timber.i("✓ Операция чтения запущена для ${characteristic.uuid}")
        } else {
            Timber.e("✗ НЕ УДАЛОСЬ запустить операцию чтения для ${characteristic.uuid}")
            // Здесь может быть много причин:
            // - Устройство отключилось
            // - Слишком много операций в очереди
            // - Неправильное состояние GATT
            // - и т.д.
        }
    }

    override fun connect() {
        disconnect()
        Timber.e("Подключение к устройству")

        _connectionState.value = ConnectionState.CONNECTING
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