package com.daniilvarezhkin.bluetoothstudio.domain.ble.scan

import kotlinx.coroutines.flow.StateFlow

interface BleScanner {
    val isScanning: StateFlow<Boolean>
    val foundDevices: StateFlow<List<BleScannedDevice>>

    //TODO Нужен список устройств, но нужно решить насчёт типа
    fun startScan()
    fun stopScan()
}