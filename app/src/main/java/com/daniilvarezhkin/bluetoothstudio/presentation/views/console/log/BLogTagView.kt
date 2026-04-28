package com.daniilvarezhkin.bluetoothstudio.presentation.views.console.log

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogTag

@Composable
fun colorByTag(tag: LogTag): Color {
    val colorScheme = MaterialTheme.colorScheme
    return when(tag) {
        // Системные и общие теги (Спокойные тона)
        LogTag.APP -> colorScheme.secondary
        LogTag.CONNECTION -> colorScheme.outline

        // Чтение и Обнаружение (Сине-голубые оттенки)
        LogTag.READ -> colorScheme.primary
        LogTag.DISCOVER -> colorScheme.primary
        LogTag.CHARACTERISTIC -> colorScheme.primary

        // Запись и Уведомления (Акцентные, но не агрессивные)
        LogTag.WRITE -> colorScheme.tertiary
        LogTag.NOTIFY -> colorScheme.inversePrimary

        // Состояние подключения (Зеленый / Красный / Желтый)
        LogTag.CONNECT -> Color(0xFF4CAF50) // Стандартный зеленый (Success)
        LogTag.SCAN -> colorScheme.secondary
        LogTag.DISCONNECT -> colorScheme.error // Ошибка или разрыв
    }
}

@Composable
fun BLogTagView(
    tag: LogTag,
    modifier: Modifier = Modifier
) {
    Text(
        text = tag.name,
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
        color = colorByTag(tag),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        textAlign = TextAlign.Start,
        modifier = modifier,
    )
}