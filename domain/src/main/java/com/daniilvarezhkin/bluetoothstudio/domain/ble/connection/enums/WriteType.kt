package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums

enum class WriteType(val code: Int) {
    DEFAULT(0x00000002),
    NO_RESPONSE(0x00000001),
    SIGNED(0x00000004),
}