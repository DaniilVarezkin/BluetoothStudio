package com.daniilvarezhkin.bluetoothstudio.presentation.views.connections.device

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MacAddressView(
    address: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = address.uppercase(),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary,
        modifier = modifier
    )
}