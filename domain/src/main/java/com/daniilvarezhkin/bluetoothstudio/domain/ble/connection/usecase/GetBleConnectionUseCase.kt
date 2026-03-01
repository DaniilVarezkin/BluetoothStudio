package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.usecase

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.BleConnection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.BleConnectionFactory
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.ConnectionError
import com.daniilvarezhkin.core.Result


class GetBleConnectionUseCase(
    private val factory: BleConnectionFactory
) {
    operator fun invoke(macAddress: String): Result<BleConnection, ConnectionError> {
        return factory.createConnection(macAddress)
    }
}