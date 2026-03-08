package com.daniilvarezhkin.bluetoothstudio.data.ble.connection.extensions


import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService

fun BluetoothGattCharacteristic.toDomainGattCharacteristic(): GattCharacteristic {
    return GattCharacteristic(
        uuid = this.uuid
    )
}

fun BluetoothGattService.toDomainGattService(): GattService {
    return GattService(
        uuid = this.uuid,
        name = "",
        includedServices = this.includedServices.map { it.toDomainGattService() },
        characteristics = this.characteristics.map { it.toDomainGattCharacteristic() }
    )
}