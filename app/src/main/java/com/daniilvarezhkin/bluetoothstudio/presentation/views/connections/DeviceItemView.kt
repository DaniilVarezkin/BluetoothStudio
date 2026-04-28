package com.daniilvarezhkin.bluetoothstudio.presentation.views.connections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.presentation.components.Container
import com.daniilvarezhkin.bluetoothstudio.presentation.views.connections.device.DeviceNameView
import com.daniilvarezhkin.bluetoothstudio.presentation.views.connections.device.MacAddressView
import com.daniilvarezhkin.bluetoothstudio.presentation.views.connections.device.RssiLevelView

@Composable
fun ScannedDeviceItemView(
    deviceName: String,
    address: String,
    rssi: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Container(
        modifier = modifier
            .clickable { onClick() },
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
                RssiLevelView(
                    rssi = rssi,
                    modifier = Modifier.size(16.dp)
                )
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
                Text(
                    text = stringResource(R.string.rssi_value_format, rssi),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun ScannedDeviceItemViewPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)  // маленький отступ между карточками
        ) {
            ScannedDeviceItemView(
                deviceName = "Polar H10",
                address = "43:22:23:98:12",
                rssi = -37,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
            ScannedDeviceItemView(
                deviceName = "Very Long Device Name That Should Be Truncated Properly",
                address = "AA:BB:CC:DD:EE:FF",
                rssi = -75,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
            ScannedDeviceItemView(
                deviceName = "Connected Device",
                address = "11:22:33:44:55:66",
                rssi = -45,
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}