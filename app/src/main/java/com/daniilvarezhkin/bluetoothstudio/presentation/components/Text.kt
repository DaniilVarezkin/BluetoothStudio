package com.daniilvarezhkin.bluetoothstudio.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.daniilvarezhkin.bluetoothstudio.presentation.ui.theme.Typography

@Composable
fun TextNormal(
    text: String
) {
    Text(
        text = text
    )
}

@Composable
fun TextSmall(
    text: String
) {
    Text(
        text = text,
        style = Typography.bodySmall
    )
}