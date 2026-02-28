package com.daniilvarezhkin.bluetoothstudio.data.ble.scan.repository

import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.BleScanner
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.model.BleScanResult
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.repository.BleDeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class BleDeviceRepositoryImpl(
    private val bleScanner: BleScanner
) : BleDeviceRepository {

    override fun getFoundedDeviceFlow(): Flow<BleScanResult> {
        return bleScanner.foundDevices.onEach {
            saveToHistory(it)
        }
    }

    override fun getHistoryFoundedDevice(): Flow<BleScanResult> {
        TODO("Not yet implemented")
    }

    private fun saveToHistory(scanResult: BleScanResult) {
//        TODO("Реализовать сохранение в BleDeviceStorage или чето такое")
    }
}