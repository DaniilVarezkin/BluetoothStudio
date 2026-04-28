package com.daniilvarezhkin.bluetoothstudio.data.ble.connection

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.ServiceKey

fun BluetoothGattService.createKey(address: String): ServiceKey {
    return ServiceKey(
        address = address,
        uuid = uuid,
        instanceId = instanceId
    )
}

fun BluetoothGattCharacteristic.createKey(serviceKey: ServiceKey): CharacteristicKey {
    return CharacteristicKey(
        serviceKey = serviceKey,
        uuid = uuid,
        instanceId = instanceId
    )
}

fun BluetoothGattCharacteristic.createKey(address: String): CharacteristicKey {
    val serviceKey = service.createKey(address)
    return createKey(serviceKey)
}

