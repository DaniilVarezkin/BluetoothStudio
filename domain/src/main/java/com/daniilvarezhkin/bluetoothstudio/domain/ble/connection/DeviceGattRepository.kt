package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.ServiceKey
import kotlinx.coroutines.flow.MutableStateFlow

abstract class DeviceGattRepository {
    protected abstract val services: MutableStateFlow<Map<ServiceKey, GattService>>
    protected abstract val characteristics: MutableStateFlow<Map<CharacteristicKey, GattCharacteristic>>
    protected abstract val charValues: MutableStateFlow<Map<CharacteristicKey, ByteArray?>>
    abstract fun updateServices(services: Map<ServiceKey, GattService>)
    abstract fun updateCharacteristics(characteristics: Map<CharacteristicKey, GattCharacteristic>)
}