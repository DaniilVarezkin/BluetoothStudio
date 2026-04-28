package com.daniilvarezhkin.bluetoothstudio.presentation.util

fun ByteArray.toHexString(separator: String = ""): String {
    return this.joinToString(separator) { "%02X".format(it) }
}