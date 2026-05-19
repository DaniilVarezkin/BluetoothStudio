package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils

import com.daniilvarezhkin.core.Result;

fun String.parseAsText(): ByteArray {
    return this.toByteArray(charset = Charsets.UTF_8)
}

/**
 * Парсит шестнадцатеричную запись байта или массива байтов в ByteArray, байты могут быть разделены следующими символами
 */
fun String.parseAsHex(): Result<ByteArray, ByteArrayParseError> {
    val normalizedValue = this
        .trim()
        .replace(Regex("[\\s,;]+"), "")

    if(normalizedValue.isEmpty()) {
        return Result.Success(byteArrayOf())
    }

    if (normalizedValue.length % 2 != 0) {
        return Result.Error(ByteArrayParseError.OddHexLength)
    }

    if (!normalizedValue.matches(Regex("[0-9a-fA-F]+"))) {
        return Result.Error(ByteArrayParseError.InvalidHexCharacters)
    }

    return Result.Success(
        data = normalizedValue
            .chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray())
}