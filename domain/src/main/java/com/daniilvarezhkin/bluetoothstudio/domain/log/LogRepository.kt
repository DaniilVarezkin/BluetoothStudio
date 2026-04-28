package com.daniilvarezhkin.bluetoothstudio.domain.log

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import kotlinx.coroutines.flow.SharedFlow

interface LogRepository {
    val logs: SharedFlow<BLog>
    fun addBLog(
        priority: Int,
        tag: LogTag,
        message: String,
        address: String? = null,
        deviceName: String? = null,
        charKey: CharacteristicKey? = null,
        rawData: ByteArray? = null
    )
}