package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daniilvarezhkin.bluetoothstudio.R

@Composable
fun DeviceConnectionScreen(
    macAddress: String,
    vm: DeviceConnectionViewModel = hiltViewModel(
        creationCallback = { factory: DeviceConnectionViewModel.Factory ->
            factory.create(macAddress)
        }
    )
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    DeviceConnectionScreenContent(uiState)
}

@Composable
fun DeviceConnectionScreenContent(
    uiState: DeviceConnectionUiState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Состояние соединения: ${uiState.connectionState.name}")
        Text("Name: ${uiState.name ?: stringResource(R.string.unknown_name)}")
        Text("Mac: ${uiState.macAddress}")
    }
}