package com.daniilvarezhkin.bluetoothstudio.domain.ble.connections

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.ConnectionError
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.DeviceConnection
import com.daniilvarezhkin.core.Result
import kotlinx.coroutines.flow.StateFlow

interface BleConnectionManager {
    val managedConnections: StateFlow<Map<String, DeviceConnection>>

    /**
     * Предоставляет подключение с устройством по указанному адресу, если подключение ещё не создано, то создаёт его и сохраняет его в список [managedConnections]
     */
    fun provideDeviceConnection(address: String): Result<DeviceConnection, ConnectionError>

    fun connect(address: String): Result<DeviceConnection, ConnectionError>

    fun reconnect(address: String): Result<DeviceConnection, ConnectionError>
    fun removeDeviceConnection(address: String)
}