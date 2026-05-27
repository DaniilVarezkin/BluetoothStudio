package com.daniilvarezhkin.bluetoothstudio.presentation.screen.scan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.presentation.views.connections.ScannedDeviceItemView

@Composable
fun ScanScreen(
    viewModel: ScanViewModel,
    onDeviceClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val devices by viewModel.foundedDevices.collectAsStateWithLifecycle(listOf())

    ScanScreenContent(
        isScanning = uiState.isScanning,
        onStartScan = viewModel::startScan,
        onStopScan = viewModel::stopScan,
        devices = devices,
        onClickDevice = { macAddress ->
            viewModel.stopScan()
            onDeviceClick(macAddress)
        },
        modifier = Modifier
            .fillMaxSize()
    )

    DisposableEffect(Unit) {
        //viewModel.startScan() //TODO подумать над тем, чтобы зависело от параметра какого-нибудь
        onDispose {
            viewModel.stopScan()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreenContent(
    isScanning: Boolean,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    devices: List<UiDevice>,
    onClickDevice: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("Сканер")
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if (isScanning) {
                        onStopScan()
                    } else {
                        onStartScan()
                    }
                }
            ) {
                Text(
                    text = if (isScanning) {
                        "Остановить"
                    } else {
                        "Начать сканирование"
                    }
                )
            }
        }
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
                .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 8.dp)
            ) {
                // TODO сделать фильтры
                Text(
                    text = if (isScanning) {
                        "Идёт поиск BLE-устройств"
                    } else {
                        "Сканирование остановлено"
                    },
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Найдено устройств: ${devices.size}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            DeviceListView(
                devices = devices,
                onClickDevice = onClickDevice,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 96.dp)
            )
        }
    }
}

@Composable
private fun DeviceListView(
    devices: List<UiDevice>,
    onClickDevice: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = contentPadding
    ) {
        items(devices, key = { it.address }) { device ->
            ScannedDeviceItemView(
                deviceName = device.name ?: stringResource(R.string.unknown_name),
                address = device.address,
                rssi = device.rssi,
                onClick = { onClickDevice(device.address) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
    }
}