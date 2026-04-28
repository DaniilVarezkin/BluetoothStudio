package com.daniilvarezhkin.bluetoothstudio.domain.ble.connections.usecase

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.DeviceConnection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connections.BleConnectionManager
import kotlinx.coroutines.flow.StateFlow

class GetActiveConnectionsUseCase(
    private val manager: BleConnectionManager
) {
    operator fun invoke(): StateFlow<Map<String, DeviceConnection>> {
        return manager.managedConnections
    }
}