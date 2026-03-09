package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.shared

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.ConnectionState
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService

data class ConnectionUiState(
    val macAddress: String,
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED,
    val deviceName: String? = null,
    val services: List<GattService> = emptyList()
)