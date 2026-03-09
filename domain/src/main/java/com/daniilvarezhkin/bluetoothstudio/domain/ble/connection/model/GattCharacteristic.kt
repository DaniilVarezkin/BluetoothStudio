package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.CharacteristicProperty
import kotlinx.coroutines.flow.MutableStateFlow

import java.util.UUID

data class GattCharacteristic(
    val uuid: UUID,
    val shortUuid: String?,
    val name: String?,
    val serviceUuid: UUID,
    val properties: List<CharacteristicProperty>,
    val value: MutableStateFlow<ByteArray?> = MutableStateFlow(null)
)
