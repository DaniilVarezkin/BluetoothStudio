package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.command

import com.daniilvarezhkin.core.Error

sealed interface CommandError : Error {
    data object NoConnection : CommandError
    data object NotSuccess : CommandError
    data object Timeout : CommandError
}