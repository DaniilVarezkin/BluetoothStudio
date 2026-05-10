package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils

fun String.parseAsFormat(format: CharWriteFormat): ByteArray = when(format) {
    CharWriteFormat.BYTE_ARRAY -> TODO()
    CharWriteFormat.TEXT -> TODO()
    CharWriteFormat.UINT_8 -> TODO()
}

fun String.parseAsText(): ByteArray {
    return this.toByteArray(charset = Charsets.UTF_8)
}

fun String.parseAsHex(): ByteArray {
    val normalizedValue = this
        .trim()

    if(normalizedValue.isEmpty()) {
        throw IllegalArgumentException("The string does not contain any parsed characters")
    }

    if(normalizedValue.length % 2 != 0) {
        throw IllegalArgumentException("The string contains an odd number of elements")
    }

    if (!normalizedValue.matches(Regex("[0-9a-fA-F]+"))) {
        throw IllegalArgumentException("The string contains invalid characters")
    }

    return normalizedValue
        .chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()
}