package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model

import java.util.UUID

data class GattService(
    val uuid: UUID,
    val shortUuid: String?,
    val name: String?,
    val includedServices: List<GattService>,
    val characteristics: List<GattCharacteristic>
)
