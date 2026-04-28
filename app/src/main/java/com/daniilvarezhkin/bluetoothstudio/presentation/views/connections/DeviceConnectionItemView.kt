package com.daniilvarezhkin.bluetoothstudio.presentation.views.connections

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.ConnectionState
import com.daniilvarezhkin.bluetoothstudio.presentation.components.Container
import com.daniilvarezhkin.bluetoothstudio.presentation.views.connections.device.DeviceNameView
import com.daniilvarezhkin.bluetoothstudio.presentation.views.connections.device.MacAddressView

@Composable
fun ConnectionDeviceItemView(
    selected: Boolean = false,
    deviceName: String,
    address: String,
    onClick: (String) -> Unit,
    onLongClick: (String) -> Unit,
    connectionState: ConnectionState,
    modifier: Modifier = Modifier
) {
    Container(
        color =
            if (selected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surfaceContainerLow,

        modifier = modifier
            .combinedClickable(
                onClick = { onClick(address) },
                onLongClick = { onLongClick(address) }
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                DeviceNameView(
                    deviceName = deviceName,
                    modifier = Modifier.weight(1f)
                )
                ConnectionTextStatusView(connectionState)
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                MacAddressView(
                    address = address,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, widthDp = 360)
fun ConnectionDeviceItemViewPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            ConnectionDeviceItemView(
                deviceName = "HMSoft",
                address = "C1:B2:C3:D4:E5:F6",
                onClick = {},
                onLongClick = {},
                connectionState = ConnectionState.CONNECTED,
            )

            ConnectionDeviceItemView(
                selected = true,
                deviceName = "HMSoft",
                address = "C1:B2:C3:D4:E5:F6",
                onClick = {},
                onLongClick = {},
                connectionState = ConnectionState.CONNECTED,
            )
        }
    }
}


