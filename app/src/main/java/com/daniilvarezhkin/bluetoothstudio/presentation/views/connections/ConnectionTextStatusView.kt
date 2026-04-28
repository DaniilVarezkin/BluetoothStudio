package com.daniilvarezhkin.bluetoothstudio.presentation.views.connections

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.ConnectionState
import com.daniilvarezhkin.bluetoothstudio.presentation.ui.theme.Connected
import com.daniilvarezhkin.bluetoothstudio.presentation.ui.theme.Connecting
import com.daniilvarezhkin.bluetoothstudio.presentation.ui.theme.Disconnected
import com.daniilvarezhkin.bluetoothstudio.presentation.ui.theme.DiscoverService
import com.daniilvarezhkin.bluetoothstudio.presentation.ui.theme.Typography

@Composable
fun ConnectionState.getTitle(): String {
    return when(this) {
        ConnectionState.CONNECTING -> stringResource(R.string.connecting)
        ConnectionState.DISCOVER_SERVICES -> stringResource(R.string.discover_services)
        ConnectionState.CONNECTED -> stringResource(R.string.connected)
        ConnectionState.DISCONNECTING -> stringResource(R.string.disconnecting)
        ConnectionState.DISCONNECTED -> stringResource(R.string.disconnected)
    }
}

@Composable
fun ConnectionState.getColor(): Color {
    return when(this) {
        ConnectionState.CONNECTING -> Connecting
        ConnectionState.DISCOVER_SERVICES -> DiscoverService
        ConnectionState.CONNECTED -> Connected
        ConnectionState.DISCONNECTING -> Disconnected
        ConnectionState.DISCONNECTED -> Disconnected
    }
}

@Composable
fun ConnectionTextStatusView(
    status: ConnectionState,
    modifier: Modifier = Modifier
) {
    Text(
        text = status.getTitle(),
        color = status.getColor(),
        style = Typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true)
fun ConnectionTextStatusPreview() {
    ConnectionTextStatusView(ConnectionState.DISCOVER_SERVICES)
}

