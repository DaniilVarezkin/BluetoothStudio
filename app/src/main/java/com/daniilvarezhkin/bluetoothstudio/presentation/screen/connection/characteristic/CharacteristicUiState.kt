package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic

data class CharacteristicUiState(
    val characteristic: GattCharacteristic? = null,
    val value: ByteArray? = null
)
