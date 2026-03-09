package com.daniilvarezhkin.bluetoothstudio.data.ble.specification.repository

import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.model.characteristic.AssignedCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.repository.AssignedCharacteristicRepository
import java.util.UUID

class AssignedCharacteristicRepositoryImpl(
    private val characteristics: List<AssignedCharacteristic>
) : AssignedCharacteristicRepository {
    private val characteristicMap by lazy { characteristics.associateBy { it.fullUuid } }

    override fun getByUuid(uuid: UUID): AssignedCharacteristic? = characteristicMap[uuid]
}