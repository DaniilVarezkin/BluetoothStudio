package com.daniilvarezhkin.bluetoothstudio.domain.ble.scan

data class BleScannedDevice(
    val name: String?,
    val mac: String,
    val rssi: Int
)