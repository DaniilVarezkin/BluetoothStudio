package com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.repository

import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.model.characteristic.AssignedCharacteristic
import java.util.UUID

interface AssignedCharacteristicRepository {
    fun getByUuid(uuid: UUID): AssignedCharacteristic?
}