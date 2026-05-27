package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.write

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils.CharWriteFormat
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils.ByteArrayParseError
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils.isValidHexString

//TODO сделать блокировку экрана до выполнения команды на запись
@Composable
fun WriteCharFragment(
    writeCharViewModel: WriteCharViewModel,
    onWriteChar: (ByteArray) -> Unit,
) {
    val uiState by writeCharViewModel.uiState.collectAsStateWithLifecycle()

    WriteCharContent(
        uiState = uiState,
        onInputValue = writeCharViewModel::onInputValue,
        onSelectFormat = writeCharViewModel::onSelectFormat,
        onWriteChar = onWriteChar, //TODO мб вынести в модель WriteCharViewModel
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
fun WriteCharContent(
    uiState: WriteCharUiState,
    onInputValue: (String) -> Unit,
    onSelectFormat: (CharWriteFormat) -> Unit,
    onWriteChar: (ByteArray) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.write_char_value),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = stringResource(R.string.enter_value_text),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Button(
                onClick = {
                    onWriteChar(uiState.parsedBytes)
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = null
                )

                Text(
                    text = "Write",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        WriteValueFormatSelector(
            selectedFormat = uiState.selectedFormat,
            onSelectFormat = onSelectFormat
        )

        OutlinedTextField(
            value = uiState.currentInputValue,
            onValueChange = { value ->
                if(uiState.selectedFormat == CharWriteFormat.BYTE_ARRAY) {
                    if(value.isValidHexString()) {
                        onInputValue(value)
                    }
                }
                else {
                    onInputValue(value)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = when (uiState.selectedFormat) {
                        CharWriteFormat.TEXT_UTF_8 -> stringResource(R.string.write_text_format_label)
                        CharWriteFormat.BYTE_ARRAY -> stringResource(R.string.write_byte_array_format_label)
                    }
                )
            },
            placeholder = {
                Text(
                    text = when (uiState.selectedFormat) {
                        CharWriteFormat.TEXT_UTF_8 -> "Hello"
                        CharWriteFormat.BYTE_ARRAY -> "6CFF3C"
                    }
                )
            },
            supportingText = {
                Text(
                    text = if (uiState.error == null) when (uiState.selectedFormat) {
                        CharWriteFormat.TEXT_UTF_8 -> stringResource(R.string.support_write_text_format_value)
                        CharWriteFormat.BYTE_ARRAY -> stringResource(R.string.support_write_byte_array_format_value)
                    } else when (uiState.error) {
                        ByteArrayParseError.InvalidHexCharacters -> stringResource(R.string.incorrect_chars_parse_error_message)
                        ByteArrayParseError.OddHexLength -> stringResource(R.string.odd_length_parse_error_message)
                    }
                )
            },
            isError = uiState.error != null,
            minLines = 4,
            maxLines = 8,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.Monospace
            ),
            shape = MaterialTheme.shapes.small,
        )

        WriteValuePreview(
            bytes = uiState.parsedBytes,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 310)
fun WriteCharContentPreview() {
    WriteCharContent(
        uiState = WriteCharUiState(),
        onInputValue = {},
        onSelectFormat = {},
        onWriteChar = {},
        modifier = Modifier.fillMaxSize(),
    )
}