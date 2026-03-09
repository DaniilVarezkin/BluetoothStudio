package com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.repository

import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.model.service.AssignedService
import java.util.UUID

interface AssignedServiceRepository {
    fun getByUuid(uuid: UUID): AssignedService?
}