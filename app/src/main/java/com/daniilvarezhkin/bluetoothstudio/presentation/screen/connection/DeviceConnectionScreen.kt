package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.presentation.views.ConnectionTextStatusView
import com.daniilvarezhkin.bluetoothstudio.presentation.views.GattServiceListView

@Composable
fun DeviceConnectionScreen(
    viewModel: DeviceConnectionViewModel,
    onClickBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DeviceConnectionScreenContent(
        uiState,
        onClickBack,
        viewModel::readCharacteristic,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceConnectionScreenContent(
    uiState: DeviceConnectionUiState,
    onClickBack: () -> Unit,
    onClickReadCharacteristic: (GattCharacteristic) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "",
                        )
                    }
                },
                title = { Text(text = uiState.deviceName ?: stringResource(R.string.unknown_name)) },
                actions = {
                    ConnectionTextStatusView(
                        status = uiState.connectionState,
                        modifier = Modifier.padding(end = 16.dp) //TODO убрать хардкор
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text("${stringResource(R.string.address)}: ${uiState.macAddress}")
            GattServiceListView(
                services = uiState.services,
                onClickReadCharacteristic = onClickReadCharacteristic,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}