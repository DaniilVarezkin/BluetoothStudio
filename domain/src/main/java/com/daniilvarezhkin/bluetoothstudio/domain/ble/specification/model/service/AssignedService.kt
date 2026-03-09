package com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.model.service

import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.model.AssignedNumber

/**
 * Определение стандартного сервиса из спецификации Bluetooth SIG
 */
data class AssignedService(
    override val uuid: String,
    val name: String,
    val id: String
) : AssignedNumber()
