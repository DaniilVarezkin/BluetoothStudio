package com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.repository

import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.model.BleScanResult
import kotlinx.coroutines.flow.Flow

interface BleDeviceRepository {
    fun getFoundedDeviceFlow(): Flow<BleScanResult>

    fun getHistoryFoundedDevice(): Flow<BleScanResult>
}