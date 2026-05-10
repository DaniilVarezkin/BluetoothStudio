package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.write

sealed class WriteValueParseResult {
    data class Success(
        val bytes: ByteArray
    ) : WriteValueParseResult()

    data class Error(
        val message: String
    ) : WriteValueParseResult()
}