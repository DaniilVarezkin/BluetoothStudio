package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.read

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.daniilvarezhkin.bluetoothstudio.R

@Composable
fun ReadCharContent(
    value: ByteArray?,
    onReadChar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "Value: ${value?.decodeToString()}")
        Button(
            onClick = onReadChar
        ) {
            Text("Прочитать")
        }
    }
}