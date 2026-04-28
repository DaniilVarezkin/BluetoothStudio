package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.ConnectionState
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey

data class ConnectionUiState(
    val address: String,
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED,
    val name: String? = null,
    val services: List<GattService> = emptyList(),
    val characteristics: List<GattCharacteristic> = emptyList(),
    val values: Map<CharacteristicKey, ByteArray?> = emptyMap()
)