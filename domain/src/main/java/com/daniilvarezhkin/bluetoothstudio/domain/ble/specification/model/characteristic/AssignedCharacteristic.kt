package com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.model.characteristic

import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.model.AssignedNumber

data class AssignedCharacteristic(
    override val uuid: String,
    val name: String,
    val id: String
) : AssignedNumber()