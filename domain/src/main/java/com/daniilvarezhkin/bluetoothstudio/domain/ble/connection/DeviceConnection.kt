package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.ConnectionState
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.command.Command
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.ServiceKey
import kotlinx.coroutines.flow.StateFlow

interface DeviceConnection {
    val address: String
    val deviceName: String?
    val connectionState: StateFlow<ConnectionState>
    val services: StateFlow<Map<ServiceKey, GattService>>
    val characteristics: StateFlow<Map<CharacteristicKey, GattCharacteristic>>
    val charValues: StateFlow<Map<CharacteristicKey, ByteArray?>>
    fun connect()
    fun disconnect()

    fun sendCommand(command: Command)

}