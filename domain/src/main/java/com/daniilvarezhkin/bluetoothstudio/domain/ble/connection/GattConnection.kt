package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection

import com.daniilvarezhkin.bluetoothstudio.domain.Connection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.command.Command
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey

interface GattConnection : Connection {

    fun onReadCharacteristic(charKey: CharacteristicKey, value: ByteArray, status: Int)
    fun onWriteCharacteristic(charKey: CharacteristicKey, status: Int)

    fun sendCommand(command: Command)

}