package com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.loader

import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.model.service.AssignedServiceList

interface AssignedServiceListLoader {
    suspend fun load(): AssignedServiceList
}