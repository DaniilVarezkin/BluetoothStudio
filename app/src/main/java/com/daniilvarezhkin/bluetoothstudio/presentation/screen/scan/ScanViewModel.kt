package com.daniilvarezhkin.bluetoothstudio.presentation.screen.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.model.BleScannedDevice
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase.GetFoundedDevicesUseCase
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase.GetScanStateUseCase
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase.StartScanBleUseCase
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase.StopScanBleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val startScanUseCase: StartScanBleUseCase,
    private val stopScanUseCase: StopScanBleUseCase,
    private val getScanStateUseCase: GetScanStateUseCase,
    private val getFoundedDevicesUseCase: GetFoundedDevicesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScanScreenUiState())
    val uiState = _uiState.asStateFlow()

    private var _foundedDevices = MutableStateFlow<List<BleScannedDevice>>(emptyList())
    val foundedDevices = _foundedDevices.asStateFlow()

    init {
        //TODO  узнать как лучше запускать корутины в viewModel
        viewModelScope.launch {
            getScanStateUseCase().collect { scanState ->
                _uiState.update { _uiState.value.copy(
                    isScanning = scanState
                ) }
            }
        }

        observeScannedDevices()
    }

    fun startScan() {
        startScanUseCase()
    }

    fun stopScan() {
        stopScanUseCase()
    }

    fun connectToDevice(macAddress: String) {
        Timber.i("Соединение с $macAddress")
    }

    private fun observeScannedDevices() = viewModelScope.launch {
        getFoundedDevicesUseCase().collect { devices ->
            _foundedDevices.value = devices
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("onCleared: ${this::class.simpleName}")
        stopScan()
    }
}