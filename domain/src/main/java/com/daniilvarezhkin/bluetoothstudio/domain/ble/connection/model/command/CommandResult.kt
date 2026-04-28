package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.command

import com.daniilvarezhkin.core.Result

sealed interface CommandResult {
    class Read(val result: ByteArray?) : CommandResult
    object Write : CommandResult
}