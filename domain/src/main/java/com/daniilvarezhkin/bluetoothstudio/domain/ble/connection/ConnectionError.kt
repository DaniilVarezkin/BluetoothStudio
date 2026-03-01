package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection

import com.daniilvarezhkin.core.Error


sealed interface ConnectionError : Error {
    data object InvalidMacAddress : ConnectionError
    data object BluetoothIsNotEnabled : ConnectionError
    data object BluetoothIsNotSupported : ConnectionError
    data class UnknownError(val throwable: Throwable) : ConnectionError
}