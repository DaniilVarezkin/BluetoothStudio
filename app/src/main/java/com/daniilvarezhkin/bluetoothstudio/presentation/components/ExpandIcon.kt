package com.daniilvarezhkin.bluetoothstudio.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp

@Composable
fun ExpandIcon(
    isExpanded: Boolean,
    modifier: Modifier = Modifier
) {
    val angle by animateFloatAsState(
        targetValue = if(isExpanded) 0f else -90f
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .rotate(angle),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}