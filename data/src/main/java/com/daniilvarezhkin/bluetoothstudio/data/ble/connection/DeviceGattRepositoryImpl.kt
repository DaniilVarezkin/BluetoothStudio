package com.daniilvarezhkin.bluetoothstudio.data.ble.connection

import android.bluetooth.BluetoothGattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.DeviceGattRepository
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.ServiceKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.concurrent.ConcurrentHashMap

class DeviceGattRepositoryImpl(
    override val services: MutableStateFlow<Map<ServiceKey, GattService>>,
    override val characteristics: MutableStateFlow<Map<CharacteristicKey, GattCharacteristic>>,
    override val charValues: MutableStateFlow<Map<CharacteristicKey, ByteArray?>>,
) : DeviceGattRepository() {
    private val gattObjectsIndex: ConcurrentHashMap<CharacteristicKey, BluetoothGattCharacteristic> = ConcurrentHashMap()

    fun getServiceFlow(): StateFlow<Map<ServiceKey, GattService>> {
        return services.asStateFlow()
    }

    fun getCharacteristicsFlow(): StateFlow<Map<CharacteristicKey, GattCharacteristic>> {
        return characteristics.asStateFlow()
    }

    fun getCharValuesFlow(): StateFlow<Map<CharacteristicKey, ByteArray?>> {
        return charValues.asStateFlow()
    }

    override fun updateServices(services: Map<ServiceKey, GattService>) {
        this.services.update { services }
    }

    override fun updateCharacteristics(characteristics: Map<CharacteristicKey, GattCharacteristic>) {
        this.characteristics.update { characteristics }
    }

    fun updateCharValue(charKey: CharacteristicKey, value: ByteArray?) {
        charValues.update { it + (charKey to value) }
    }

    fun addGattObjectIndex(key: CharacteristicKey, bluetoothGattCharacteristic: BluetoothGattCharacteristic) {
        gattObjectsIndex[key] = bluetoothGattCharacteristic
    }

    fun getRawChar(charKey: CharacteristicKey): BluetoothGattCharacteristic? {
        return gattObjectsIndex[charKey]
    }
}