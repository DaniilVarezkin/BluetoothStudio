package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.write

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils.CharWriteFormat

@Composable
fun WriteCharContent(
    onWriteChar: (ByteArray) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFormat by rememberSaveable {
        mutableStateOf(CharWriteFormat.BYTE_ARRAY)
    }

    var inputValue by rememberSaveable {
        mutableStateOf("")
    }

    val parseResult = remember(inputValue, selectedFormat) {
        byteArrayOf(127)
//        inputValue.parseAsWriteValue(selectedFormat)
    }

    val bytesToWrite = parseResult

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
                enabled = bytesToWrite != null,
                onClick = {
                    bytesToWrite?.let(onWriteChar)
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
            selectedFormat = selectedFormat,
            onSelectFormat = {
                selectedFormat = it
            }
        )

        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    text = when (selectedFormat) {
                        CharWriteFormat.TEXT -> stringResource(R.string.write_text_format_label)
                        CharWriteFormat.BYTE_ARRAY -> stringResource(R.string.write_byte_array_format_label)
                        CharWriteFormat.UINT_8 -> stringResource(R.string.write_uint8_format_label)
                    }
                )
            },
            placeholder = {
                Text(
                    text = when (selectedFormat) {
                        CharWriteFormat.TEXT -> "Hello"
                        CharWriteFormat.BYTE_ARRAY -> "6CFF3C"
                        CharWriteFormat.UINT_8 -> "255"
                    }
                )
            },
            supportingText = {
                Text(
                    text = when (selectedFormat) {
                        CharWriteFormat.TEXT -> stringResource(R.string.support_write_text_format_value)
                        CharWriteFormat.BYTE_ARRAY -> stringResource(R.string.support_write_byte_array_format_value)
                        CharWriteFormat.UINT_8 -> stringResource(R.string.support_write_uint_8_format_value)
                    }
                )
            },
            minLines = 4,
            maxLines = 8,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.Monospace
            ),
            shape = MaterialTheme.shapes.small
        )

        WriteValuePreview(
            bytes = bytesToWrite,
            modifier = Modifier.fillMaxWidth()
        )
    }
}