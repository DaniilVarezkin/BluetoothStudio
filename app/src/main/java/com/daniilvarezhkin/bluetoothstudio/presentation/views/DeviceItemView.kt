package com.daniilvarezhkin.bluetoothstudio.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.presentation.components.TextNormal
import com.daniilvarezhkin.bluetoothstudio.presentation.components.TextSmall

@Composable
fun DeviceItemView(
    name: String,
    macAddress: String,
    rssi: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column() {
            TextNormal(name)
            TextSmall(macAddress)
            TextSmall("$rssi ${stringResource(R.string.rssi_unit)}")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DeviceItemViewPreview() {
    DeviceItemView(
        "Устройство",
        "43:22:23:98:12",
        37,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    )
}