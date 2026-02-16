package com.daniilvarezhkin.bluetoothstudio.presentation.screen.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase.GetScanStateUseCase
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase.StartScanBleUseCase
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase.StopScanBleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val startScanUseCase: StartScanBleUseCase,
    private val stopScanUseCase: StopScanBleUseCase,
    private val getScanStateUseCase: GetScanStateUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScanScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        //TODO  узнать как лучше запускать корутины в viewModel
        viewModelScope.launch {
            getScanStateUseCase().collect { scanState ->
                _uiState.update { _uiState.value.copy(
                    isScanning = scanState
                ) }
            }
        }
    }

    fun startScan() {
        startScanUseCase()
    }

    fun stopScan() {
        stopScanUseCase()
    }
}