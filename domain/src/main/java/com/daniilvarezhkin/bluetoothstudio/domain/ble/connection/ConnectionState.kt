package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection

enum class ConnectionState {
    CONNECTING,
    DISCOVER_SERVICES,
    CONNECTED,
    DISCONNECTED
}