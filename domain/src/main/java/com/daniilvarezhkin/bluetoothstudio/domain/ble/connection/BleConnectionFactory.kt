package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection

import com.daniilvarezhkin.core.Result

interface BleConnectionFactory {
    fun createConnection(macAddress: String): Result<BleConnection, ConnectionError>
}
