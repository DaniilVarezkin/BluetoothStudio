package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils

fun ByteArray.formatAs(format: CharReadFormat): String = when(format) {
    CharReadFormat.TEXT -> this.formatAsText()
    CharReadFormat.HEX -> this.formatAsHex()
    CharReadFormat.DEC -> this.formatAsDec()
}

fun ByteArray.formatAsText(): String {
    return this.decodeToString()
}

fun ByteArray.formatAsHex(): String {
    return this.joinToString(separator = " ") { byte ->
        "%02X".format(byte.toInt() and 0xFF)
    }
}

fun ByteArray.formatAsDec(): String {
    return this.joinToString(separator = " ") { byte ->
        (byte.toInt() and 0xFF).toString()
    }
}