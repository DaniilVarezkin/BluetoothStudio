package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.command

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.WriteType
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.core.Result

sealed interface Command {
    val onGetResult: (Result<CommandResult, CommandError>) -> Unit

    data class Read(
        val charKey: CharacteristicKey,
        override val onGetResult: (Result<CommandResult, CommandError>) -> Unit = {},
    ) : Command

    data class Write(
        val charKey: CharacteristicKey,
        val value: ByteArray,
        val writeType: WriteType,
        override val onGetResult: (Result<CommandResult, CommandError>) -> Unit = {},
    ) : Command
}