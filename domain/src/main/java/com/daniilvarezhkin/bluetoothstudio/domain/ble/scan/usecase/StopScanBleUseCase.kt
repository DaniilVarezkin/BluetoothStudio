package com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase

import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.BleScanner

class StopScanBleUseCase(
    val bleScanner: BleScanner
) {
    operator fun invoke() {
        bleScanner.stopScan()
    }
}