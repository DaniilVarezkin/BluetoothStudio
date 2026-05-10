package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.read

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.daniilvarezhkin.bluetoothstudio.presentation.components.SelectableBadge
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.utils.CharReadFormat


@Composable
fun CharValueFormatSelector(
    selectedFormat: CharReadFormat,
    onSelectFormat: (CharReadFormat) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CharReadFormat.entries.forEach { format ->
            SelectableBadge(
                label = format.label,
                isSelected = selectedFormat == format,
                onSelect = { onSelectFormat(format) }
            )
        }
    }
}