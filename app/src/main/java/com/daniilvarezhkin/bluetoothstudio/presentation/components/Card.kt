package com.daniilvarezhkin.bluetoothstudio.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Card(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = modifier
    ) {
        content()
    }
}

@Composable
@Preview(showBackground = true)
fun CardPreview() {
    Card() {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Red)
        )
    }
}