package com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.model

data class BleScanResult (
    val devices: List<BleScannedDevice> = emptyList()
)