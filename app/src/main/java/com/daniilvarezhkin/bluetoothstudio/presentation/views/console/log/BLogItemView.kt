package com.daniilvarezhkin.bluetoothstudio.presentation.views.console.log

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.ServiceKey
import com.daniilvarezhkin.bluetoothstudio.domain.log.BLog
import com.daniilvarezhkin.bluetoothstudio.domain.log.BLogMetadata
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogTag
import com.daniilvarezhkin.bluetoothstudio.presentation.components.ExpandIcon
import com.daniilvarezhkin.bluetoothstudio.presentation.util.toHexString
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID

private val LogTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")
    .withZone(ZoneId.systemDefault())

private object LogTableConfig {
    const val TIME_WEIGHT = 0.25f
    const val TAG_WEIGHT = 0.2f
    const val MESSAGE_WEIGHT = 0.50f
}

@Composable
fun BLogItemView(
    log: BLog,
    modifier: Modifier = Modifier,
    showTimestamp: Boolean = true,
) {
    val formattedTime = remember(log.timestamp) {
        LogTimeFormatter.format(Instant.ofEpochMilli(log.timestamp))
    }

    var isExpanded by remember { mutableStateOf(false) }
    val background = when (log.priority) {
        Log.ERROR -> MaterialTheme.colorScheme.errorContainer
        Log.WARN -> Color.Yellow
        else -> Color.Transparent
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(background)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { isExpanded = !isExpanded }),
        ) {
            ExpandIcon(
                isExpanded = isExpanded,
                modifier = Modifier
                    .alignByBaseline()
            )

            if (showTimestamp) {
                Text(
                    text = formattedTime,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(LogTableConfig.TIME_WEIGHT)
                        .alignByBaseline(),
                )
            }

            BLogTagView(
                tag = log.tag,
                modifier = Modifier
                    .weight(LogTableConfig.TAG_WEIGHT)
                    .alignByBaseline()
            )

            Text(
                text = log.message,
                style = MaterialTheme.typography.labelSmall,
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                modifier = Modifier
                    .weight(LogTableConfig.MESSAGE_WEIGHT)
                    .alignByBaseline(),
            )
        }

        if (isExpanded) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(Modifier.width(dimensionResource(R.dimen.horizontal_screen_padding)))

                // Контент деталей занимает всё остальное место (0.95f)
                BLogItemDetailView(
                    log = log,
                    modifier = Modifier
                        .weight(
                            with(LogTableConfig) {
                                TIME_WEIGHT + TAG_WEIGHT + MESSAGE_WEIGHT
                            }
                        )
                )
            }
        }
    }
}

@Composable
fun BLogItemDetailView(
    log: BLog,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Metadata:",
            style = MaterialTheme.typography.labelSmall,
            fontFamily = FontFamily.Monospace,
        )

        val address = log.address
        if (address != null) {
            BLogMetadataItemView(
                keyName = "Address",
                value = address
            )
        }

        log.metadata?.deviceName?.let { deviceName ->
            BLogMetadataItemView(
                keyName = "Device name",
                value = deviceName
            )
        }

        log.metadata?.charKey?.let { charKey ->
            BLogMetadataItemView(
                keyName = "Service",
                value = charKey.serviceKey.uuid.toString()
            )

            BLogMetadataItemView(
                keyName = "Char",
                value = charKey.uuid.toString()
            )
        }

        log.metadata?.rawData?.let { rawData ->
            BLogMetadataItemView(
                keyName = "Raw data",
                value = rawData.toHexString()
            )
        }
    }
}

@Composable
fun BLogMetadataItemView(
    keyName: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$keyName: ",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontFamily = FontFamily.Monospace,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
@Preview(showBackground = true, widthDp = 300)
fun BLogItemViewPreview() {
    val log = BLog(
        id = 1,
        timestamp = 1712253000553L,
        priority = 3,
        tag = LogTag.READ,
        message = "Обновлен результат сканирования результат сканирования Обновлен результат сканирования результат сканирования Обновлен результат сканирования результат сканирования Обновлен результат сканирования результат сканирования",
        address = "C1:B2:C3:D4:E5:F6",
        metadata = BLogMetadata(
            charKey = CharacteristicKey(
                serviceKey = ServiceKey("AA:32:R4:42:22", UUID.randomUUID(), 1),
                uuid = UUID.randomUUID(),
                instanceId = 3
            ),
            deviceName = "HMSoft",
            rawData = byteArrayOf(127, 127, 127)
        ),

        )
    BLogItemView(
        log = log,
        showTimestamp = true
    )
}