package com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.model.service

/**
 * Список стандартных сервисов определенных в спецификации Bluetooth SIG
 */
data class AssignedServiceList(
    var uuids: List<AssignedService>
)
