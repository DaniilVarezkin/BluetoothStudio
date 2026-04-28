package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.write

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun WriteCharContent(
    onWriteChar: (ByteArray) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        var value by remember { mutableStateOf("") }
        TextField(
            value = value,
            onValueChange = {
                value = it
            }
        )
        Button(
            onClick = {onWriteChar(value.toByteArray(Charsets.US_ASCII))}
        ) {
            Text("Отправить")
        }
    }
}