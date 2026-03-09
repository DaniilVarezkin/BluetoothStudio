package com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.model

import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.common.BLUETOOTH_BASE_UUID
import java.util.UUID

abstract class AssignedNumber() {
    abstract val uuid: String
    val fullUuid: UUID by lazy {
        val hex = uuid
            .lowercase()
            .removePrefix("0x")
            .padStart(8, '0')
        try {
            UUID.fromString(BLUETOOTH_BASE_UUID.replaceFirst("00000000", hex))
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid UUID hex format: $uuid", e)
        }
    }
}