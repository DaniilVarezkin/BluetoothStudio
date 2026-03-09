package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.ConnectionState
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService

data class DeviceConnectionUiState(
    val macAddress: String,
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED,
    val deviceName: String? = null,
    val services: List<GattService> = emptyList()
)