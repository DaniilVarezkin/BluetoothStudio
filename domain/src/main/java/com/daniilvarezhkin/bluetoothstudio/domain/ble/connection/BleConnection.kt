package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.ConnectionState
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService
import kotlinx.coroutines.flow.StateFlow

interface BleConnection {
    val connectionState: StateFlow<ConnectionState>
    val gattServices: StateFlow<List<GattService>>
    fun readCharacteristic(characteristic: GattCharacteristic)
    fun connect();
    fun disconnect();
}