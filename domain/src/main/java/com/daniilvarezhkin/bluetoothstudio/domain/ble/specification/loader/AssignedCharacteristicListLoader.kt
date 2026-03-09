package com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.loader

import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.model.characteristic.AssignedCharacteristicList

interface AssignedCharacteristicListLoader {
    suspend fun load(): AssignedCharacteristicList
}