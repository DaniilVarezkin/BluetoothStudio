package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model

import java.util.UUID

data class GattDescriptor(
    val uuid: UUID,
    val value: ByteArray? = null
)