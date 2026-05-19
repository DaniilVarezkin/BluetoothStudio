package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.write

import androidx.lifecycle.ViewModel
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils.CharWriteFormat
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils.ByteArrayParseError
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils.parseAsHex
import com.daniilvarezhkin.core.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class WriteCharViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(WriteCharUiState())
    val uiState = _uiState.asStateFlow()

    fun onInputValue(value: String) {
        // Обновление значения для выбранного формата
        val inputValues = uiState.value.inputValues + (uiState.value.selectedFormat to value)
        val parsedResult = parseValue(value, uiState.value.selectedFormat)

        _uiState.update {
            it.copy(
                inputValues = inputValues,
                parsedBytes = parsedResult.getDataOrNull() ?: byteArrayOf(),
                error = parsedResult.getErrorOrNull()
            )
        }
    }

    fun onSelectFormat(format: CharWriteFormat) {
        val value = uiState.value.inputValues[format].orEmpty()
        val parsedResult = parseValue(value, format)

        _uiState.update {
            it.copy(
                selectedFormat = format,
                parsedBytes = parsedResult.getDataOrNull() ?: byteArrayOf(),
                error = parsedResult.getErrorOrNull()
            )
        }
    }

    private fun parseValue(value: String, format: CharWriteFormat): Result<ByteArray, ByteArrayParseError> {
        return when (format) {
            CharWriteFormat.BYTE_ARRAY -> value.parseAsHex()
            CharWriteFormat.TEXT_UTF_8 -> Result.Success(value.toByteArray())
        }
    }
}