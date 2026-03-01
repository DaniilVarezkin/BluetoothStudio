package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection

import kotlinx.coroutines.flow.StateFlow

interface BleConnection {
    val connectionState: StateFlow<ConnectionState>
    fun connect();
    fun disconnect();
}