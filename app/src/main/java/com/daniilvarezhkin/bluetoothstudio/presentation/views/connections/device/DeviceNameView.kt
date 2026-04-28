package com.daniilvarezhkin.bluetoothstudio.presentation.views.connections.device

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DeviceNameView(
    deviceName: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = deviceName,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
    )
}