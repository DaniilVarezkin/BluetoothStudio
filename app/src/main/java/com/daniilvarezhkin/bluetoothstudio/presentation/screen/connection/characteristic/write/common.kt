package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.write

//fun String.parseAsWriteValue(
//    format: WriteValueFormat
//): WriteValueParseResult {
//    val trimmedValue = trim()
//
//    if (trimmedValue.isEmpty()) {
//        return WriteValueParseResult.Error("Enter value")
//    }
//
//    return when (format) {
//        WriteValueFormat.TEXT -> {
//            WriteValueParseResult.Success(
//                bytes = trimmedValue.toByteArray(Charsets.UTF_8)
//            )
//        }
//
//        WriteValueFormat.HEX -> {
//            parseHexValue(trimmedValue)
//        }
//
//        WriteValueFormat.DEC -> {
//            parseDecimalValue(trimmedValue)
//        }
//    }
//}
//
//private fun parseHexValue(
//    value: String
//): WriteValueParseResult {
//    val normalizedValue = value
//        .replace("0x", "", ignoreCase = true)
//        .replace(Regex("[\\s,;:_-]+"), "")
//
//    if (normalizedValue.isEmpty()) {
//        return WriteValueParseResult.Error("Enter HEX value")
//    }
//
//    if (normalizedValue.length % 2 != 0) {
//        return WriteValueParseResult.Error("HEX value should contain even number of characters")
//    }
//
//    if (!normalizedValue.matches(Regex("[0-9a-fA-F]+"))) {
//        return WriteValueParseResult.Error("HEX value contains invalid characters")
//    }
//
//    val bytes = normalizedValue
//        .chunked(2)
//        .map { it.toInt(16).toByte() }
//        .toByteArray()
//
//    return WriteValueParseResult.Success(bytes)
//}
//
//private fun parseDecimalValue(
//    value: String
//): WriteValueParseResult {
//    val parts = value
//        .split(Regex("[\\s,;]+"))
//        .filter { it.isNotBlank() }
//
//    if (parts.isEmpty()) {
//        return WriteValueParseResult.Error("Enter decimal bytes")
//    }
//
//    val bytes = mutableListOf<Byte>()
//
//    parts.forEach { part ->
//        val number = part.toIntOrNull()
//            ?: return WriteValueParseResult.Error("Invalid decimal value: $part")
//
//        if (number !in 0..255) {
//            return WriteValueParseResult.Error("Decimal byte should be from 0 to 255: $part")
//        }
//
//        bytes += number.toByte()
//    }
//
//    return WriteValueParseResult.Success(bytes.toByteArray())
//}
//
//private fun ByteArray.toHexString(): String {
//    return joinToString(separator = " ") { byte ->
//        "%02X".format(byte.toInt() and 0xFF)
//    }
//}