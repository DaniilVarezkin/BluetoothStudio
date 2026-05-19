package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.write

import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils.CharWriteFormat
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils.ByteArrayParseError

data class WriteCharUiState(
    val selectedFormat: CharWriteFormat = CharWriteFormat.TEXT_UTF_8,
    val inputValues: Map<CharWriteFormat, String> = CharWriteFormat.entries.associateWith { "" },
    val error: ByteArrayParseError? = null,
    val parsedBytes: ByteArray = byteArrayOf(),
) {
    val currentInputValue: String
        get() = inputValues[selectedFormat].orEmpty()
}
