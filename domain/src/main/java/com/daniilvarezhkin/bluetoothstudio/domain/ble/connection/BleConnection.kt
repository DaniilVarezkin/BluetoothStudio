package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService
import kotlinx.coroutines.flow.StateFlow

interface BleConnection {
    val connectionState: StateFlow<ConnectionState>
    val gattServices: StateFlow<List<GattService>>
    fun connect();
    fun disconnect();
}