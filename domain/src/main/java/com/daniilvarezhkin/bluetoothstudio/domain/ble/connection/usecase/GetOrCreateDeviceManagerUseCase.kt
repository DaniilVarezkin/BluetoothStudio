package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.usecase

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.ConnectionError
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.DeviceConnection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connections.BleConnectionManager
import com.daniilvarezhkin.core.Result


class GetOrCreateDeviceManagerUseCase(
    private val manager: BleConnectionManager
) {
    operator fun invoke(macAddress: String): Result<DeviceConnection, ConnectionError> {
        return manager.provideDeviceConnection(macAddress)
    }
}