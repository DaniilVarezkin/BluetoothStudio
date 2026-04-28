package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connections

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.DeviceConnection

data class ConnectionsUiState(
    val isSelectedState: Boolean = false,
    val connections: List<DeviceConnection> = emptyList(),
    val selectedAddresses: Set<String> = emptySet(),
)