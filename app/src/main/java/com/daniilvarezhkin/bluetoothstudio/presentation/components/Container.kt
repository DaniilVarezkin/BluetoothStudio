package com.daniilvarezhkin.bluetoothstudio.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Container(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceContainerLow,
    content: @Composable (() -> Unit)
) {
    Surface(
        modifier = modifier,
        color = color,
        tonalElevation = 1.dp,
        shape = MaterialTheme.shapes.small,
    ) {
        content()
    }
}