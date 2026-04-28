package com.daniilvarezhkin.bluetoothstudio.presentation.views.connections.device

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RssiLevelView(
    rssi: Int,
    modifier: Modifier = Modifier
) {
    val bars = when {
        rssi >= -50 -> 4
        rssi >= -70 -> 3
        rssi >= -85 -> 2
        else -> 1
    }

    val activeColor = when (bars) {
        4 -> Color(0xFF4CAF50) // Зеленый
        3 -> Color(0xFF8BC34A) // Салатовый
        2 -> Color(0xFFFFC107) // Желтый
        else -> Color(0xFFF44336) // Красный
    }

    Row(
        modifier = modifier
            .width(24.dp)
            .height(16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        (1..4).forEach { index ->
            val isActive = index <= bars
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.25f * index)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (isActive) activeColor
                        else MaterialTheme.colorScheme.outlineVariant
                    )

            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RssiLevelViewPreview() {
    RssiLevelView(-80)
}