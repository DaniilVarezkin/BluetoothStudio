package com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.model

data class BleScannedDevice(
    val name: String?,
    val mac: String,
    val rssi: Int
)