package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.ConnectionState

data class DeviceConnectionUiState(
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED,
    val name: String? = null,
    val macAddress: String,
)