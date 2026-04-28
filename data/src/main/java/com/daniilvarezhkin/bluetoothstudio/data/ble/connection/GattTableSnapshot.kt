package com.daniilvarezhkin.bluetoothstudio.data.ble.connection

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.ServiceKey

/**
 * Содержит сопоставления уникального идентификатора и значения характеристики или сервиса
 */
data class GattTableSnapshot(
    val services: Map<ServiceKey, GattService>,
    val characteristics: Map<CharacteristicKey, GattCharacteristic>
)