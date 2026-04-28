package com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.model

data class BleScannedDevice(
    val name: String?,
    val address: String,
    val rssi: Int
)