package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.CharProperty
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey

import java.util.UUID

data class GattCharacteristic(
    val key: CharacteristicKey,
    val uuid: UUID,
    val shortUuid: String?,
    val name: String?,
    val serviceUuid: UUID,
    val properties: List<CharProperty>,
)
