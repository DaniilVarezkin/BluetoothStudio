package com.daniilvarezhkin.bluetoothstudio.presentation.scan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier

@Composable
fun ScanScreen() {
    val vm: ScanViewModel = hiltViewModel()

    val uiState = vm.uiState.collectAsState()

    ScanScreenContent(
        isScanning = uiState.value.isScanning,
        onStartScan = vm::startScan,
        onStopScan = vm::stopScan
    )
}

@Composable
fun ScanScreenContent(
    isScanning: Boolean,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
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
        }
    }
}