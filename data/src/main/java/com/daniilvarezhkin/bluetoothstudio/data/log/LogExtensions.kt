package com.daniilvarezhkin.bluetoothstudio.data.log

import android.util.Log
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogRepository
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogTag

fun LogRepository.gattError(address: String, deviceName: String?, tag: LogTag, message: String) {
    addBLog(
        priority = Log.ERROR,
        tag = tag,
        message = message,
        address = address,
        deviceName = deviceName,
    )
}

fun LogRepository.gattSuccess(address: String, deviceName: String?, tag: LogTag, message: String) {
    addBLog(
        priority = Log.INFO,
        tag = tag,
        message = message,
        address = address,
        deviceName = deviceName,
    )
}

fun LogRepository.charError(charKey: CharacteristicKey, deviceName: String?, tag: LogTag, message: String) {
    addBLog(
        priority = Log.ERROR,
        tag = tag,
        message = message,
        address = charKey.serviceKey.address,
        deviceName = deviceName,
        charKey = charKey,
    )
}

fun LogRepository.charSuccess(charKey: CharacteristicKey, deviceName: String?, tag: LogTag, message: String, data: ByteArray? = null) {
    addBLog(
        priority = Log.INFO,
        tag = tag,
        message = message,
        address = charKey.serviceKey.address,
        deviceName = deviceName,
        rawData = data,
        charKey = charKey,
    )
}

fun LogRepository.charNotFound(charKey: CharacteristicKey, deviceName: String?, tag: LogTag) {
    addBLog(
        priority = Log.WARN,
        tag = tag,
        message = "The characteristic was not found in the UUID: ${charKey.uuid}",
        address = charKey.serviceKey.address,
        deviceName = deviceName,
        charKey = charKey,
    )
}