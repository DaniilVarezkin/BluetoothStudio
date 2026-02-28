package com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase

import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.BleScanner

class StartScanBleUseCase(
    private val bleScanner: BleScanner
) {
    operator fun invoke() = bleScanner.startScan()
}