package com.daniilvarezhkin.bluetoothstudio.data.ble.connections

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.BleConnectionFactory
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.ConnectionError
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.DeviceConnection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.ConnectionState
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connections.BleConnectionManager
import com.daniilvarezhkin.core.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BleConnectionManagerImpl(
    private val factory: BleConnectionFactory
) : BleConnectionManager {
    /**
     * Карта Адрес -> Устройство
     */
    private val _managedConnections: MutableStateFlow<Map<String, DeviceConnection>> = MutableStateFlow(emptyMap())
    override val managedConnections = _managedConnections.asStateFlow()

    override fun provideDeviceConnection(address: String): Result<DeviceConnection, ConnectionError> {
        val existing = _managedConnections.value[address]
        if (existing != null && existing.connectionState.value != ConnectionState.DISCONNECTED) {
            return Result.Success(existing)
        }

        return createAndStoreConnection(address)
    }

    override fun connect(address: String): Result<DeviceConnection, ConnectionError> {
        return provideDeviceConnection(address).onSuccess { it.connect() }
    }

    override fun reconnect(address: String): Result<DeviceConnection, ConnectionError> {
        _managedConnections.value[address]?.disconnect()
        return connect(address)
    }

    override fun removeDeviceConnection(address: String) {
        _managedConnections.update { currentMap ->
            val connection = currentMap[address]
            if (connection != null) {
                connection.disconnect()
                currentMap - address
            } else {
                currentMap
            }
        }
    }

    private fun createAndStoreConnection(address: String): Result<DeviceConnection, ConnectionError> {
        val result = factory.createConnection(address = address)
        if (result is Result.Success) {
            val connection = result.data
            _managedConnections.update { currentMap ->
                currentMap + (connection.address to connection)
            }
        }
        return result
    }
}