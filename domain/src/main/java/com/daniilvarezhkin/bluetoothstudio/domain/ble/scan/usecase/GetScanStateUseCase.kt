package com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase

import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.BleScanner
import kotlinx.coroutines.flow.StateFlow

class GetScanStateUseCase(
    private val bleScanner: BleScanner
) {
    operator fun invoke(): StateFlow<Boolean> {
        return bleScanner.isScanning
    }
}