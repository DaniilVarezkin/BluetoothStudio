package com.daniilvarezhkin.bluetoothstudio.domain.ble.scan

import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.model.BleScanResult
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.model.BleScannedDevice
import kotlinx.coroutines.flow.StateFlow

interface BleScanner {
    val isScanning: StateFlow<Boolean>
    val foundDevices: StateFlow<BleScanResult>
    fun startScan()
    fun stopScan()
}