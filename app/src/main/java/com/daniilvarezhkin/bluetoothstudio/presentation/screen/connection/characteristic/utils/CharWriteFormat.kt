package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils

enum class CharWriteFormat(
    val label: String
) {
    BYTE_ARRAY("Byte array"),
    TEXT("Text (Utf-8)"),
    UINT_8("uint_8")
}