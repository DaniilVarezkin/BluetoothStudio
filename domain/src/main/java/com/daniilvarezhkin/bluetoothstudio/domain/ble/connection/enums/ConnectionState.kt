package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums

enum class ConnectionState {
    CONNECTING,
    DISCOVER_SERVICES,
    CONNECTED,
    DISCONNECTING,
    DISCONNECTED
}