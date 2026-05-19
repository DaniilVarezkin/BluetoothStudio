package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils

fun String.isValidHexString(): Boolean {
    return this.matches(Regex("^[0-9a-fA-F\\s,;]*$"))
}