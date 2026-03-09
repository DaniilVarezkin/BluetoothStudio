package com.daniilvarezhkin.bluetoothstudio.data.ble.specification.repository

import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.model.service.AssignedService
import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.repository.AssignedServiceRepository
import java.util.UUID

class AssignedServiceRepositoryImpl(
    private val services: List<AssignedService>
) : AssignedServiceRepository {
    private val serviceMap by lazy { services.associateBy { it.fullUuid } }

    override fun getByUuid(uuid: UUID): AssignedService? = serviceMap[uuid]
}