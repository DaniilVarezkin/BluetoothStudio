package com.daniilvarezhkin.bluetoothstudio.presentation.views.connections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.ConnectionState
import com.daniilvarezhkin.bluetoothstudio.presentation.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectionTopBar(
    deviceName: String?,
    address: String,
    connectionState: ConnectionState,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Column {
                Text(text = deviceName ?: stringResource(R.string.unknown_name))
                Text(text = address, style = Typography.bodySmall)
            }

        },
        actions = {
            ConnectionTextStatusView(
                status = connectionState,
                modifier = Modifier.padding(end = dimensionResource(R.dimen.horizontal_screen_padding))
            )
        }
    )
}