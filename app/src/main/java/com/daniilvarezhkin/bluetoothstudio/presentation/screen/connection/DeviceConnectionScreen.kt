package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.presentation.views.connections.ConnectionTopBar
import com.daniilvarezhkin.bluetoothstudio.presentation.views.connections.GattServiceListView

@Composable
fun ConnectionScreen(
    viewModel: ConnectionViewModel,
    onClickCharacteristic: (GattCharacteristic) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ConnectionScreenContent(
        uiState = uiState,
        onClickCharacteristic = onClickCharacteristic,
        onClickReadCharacteristic = viewModel::readCharacteristic,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectionScreenContent(
    uiState: ConnectionUiState,
    onClickCharacteristic: (GattCharacteristic) -> Unit,
    onClickReadCharacteristic: (CharacteristicKey) -> Unit
) {
    Scaffold(
        topBar = {
            ConnectionTopBar(
                deviceName = uiState.name,
                address = uiState.address,
                connectionState = uiState.connectionState
            )
        }
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
                .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding)),
            verticalArrangement = Arrangement.Top
        ) {
            GattServiceListView(
                services = uiState.services,
                values = uiState.values,
                onClickCharacteristic = onClickCharacteristic,
                onClickReadCharacteristic = onClickReadCharacteristic,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}