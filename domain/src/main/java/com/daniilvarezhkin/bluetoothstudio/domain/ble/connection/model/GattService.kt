package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.ServiceKey
import java.util.UUID

data class GattService(
    val key: ServiceKey,
    val uuid: UUID,
    val shortUuid: String?,
    val name: String?,
    val characteristics: List<GattCharacteristic>
)
