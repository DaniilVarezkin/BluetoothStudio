package com.daniilvarezhkin.bluetoothstudio.domain.log

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey

data class BLogMetadata(
    val deviceName: String? = null,
    val charKey: CharacteristicKey? = null,
    val rawData: ByteArray? = null,

)
