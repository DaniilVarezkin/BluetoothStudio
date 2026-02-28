package com.daniilvarezhkin.bluetoothstudio.presentation.screen.scan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import com.daniilvarezhkin.bluetoothstudio.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.model.BleScannedDevice
import com.daniilvarezhkin.bluetoothstudio.presentation.components.CustomScaffold
import com.daniilvarezhkin.bluetoothstudio.presentation.views.DeviceItemView

@Composable
fun ScanScreen() {
    val vm: ScanViewModel = hiltViewModel()

    val uiState by vm.uiState.collectAsStateWithLifecycle()

    val devices by vm.foundedDevices.collectAsStateWithLifecycle(listOf())

    CustomScaffold() {
        ScanScreenContent(
            isScanning = uiState.isScanning,
            onStartScan = vm::startScan,
            onStopScan = vm::stopScan,
            devices = devices,
            onClickDevice = vm::connectToDevice,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@Composable
fun ScanScreenContent(
    isScanning: Boolean,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit,
    devices: List<BleScannedDevice>,
    onClickDevice: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text("Экран сканирования")
        if (isScanning) {

            Text("Сканирование запущено")
            Button(
                onClick = { onStopScan() }
            ) {
                Text("Остановить сканирование")
            }
        } else {
            Text("Сканирование отключено")
            Button(
                onClick = { onStartScan() }
            ) {
                Text("Запустить сканирование")
            }
        }
        DeviceListView(
            devices = devices,
            onClickDevice = onClickDevice,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Composable
private fun DeviceListView(
    devices: List<BleScannedDevice>,
    onClickDevice: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(devices) { device ->
            DeviceItemView(
                device.name ?: stringResource(R.string.unknown_name),
                device.mac,
                device.rssi,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_normal))
                    .clickable { onClickDevice(device.mac) }
            )
        }
    }
}