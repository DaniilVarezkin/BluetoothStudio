package com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase

import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.model.BleScannedDevice
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.repository.BleDeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFoundedDevicesUseCase(
    private val repository: BleDeviceRepository
) {
    operator fun invoke(): Flow<List<BleScannedDevice>> {
        return repository.getFoundedDeviceFlow().map { it.devices }
    }
}