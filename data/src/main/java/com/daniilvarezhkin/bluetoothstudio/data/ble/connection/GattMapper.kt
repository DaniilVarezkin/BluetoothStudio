package com.daniilvarezhkin.bluetoothstudio.data.ble.connection

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.CharacteristicProperty
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService
import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.repository.AssignedCharacteristicRepository
import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.repository.AssignedServiceRepository

class GattMapper(
    private val assignedServiceRepository: AssignedServiceRepository,
    private val assignedCharacteristicRepository: AssignedCharacteristicRepository
) {
    fun BluetoothGattService.toGattService(): GattService {
        val assignedService = assignedServiceRepository.getByUuid(this.uuid)
        return GattService(
            uuid = this.uuid,
            shortUuid = assignedService?.uuid,
            name = assignedService?.name,
            includedServices = this.includedServices.map { it.toGattService() },
            characteristics = this.characteristics.map { it.toGattCharacteristic() }
        )
    }

    fun BluetoothGattCharacteristic.toGattCharacteristic(): GattCharacteristic {
        val assignedCharacteristic = assignedCharacteristicRepository.getByUuid(this.uuid)
        return GattCharacteristic(
            uuid = this.uuid,
            shortUuid = assignedCharacteristic?.uuid,
            name = assignedCharacteristic?.name,
            serviceUuid = this.service.uuid,
            properties = decodeProperties(this.properties)
        )
    }

    private fun decodeProperties(properties: Int): List<CharacteristicProperty> {
        val propertiesList: MutableList<CharacteristicProperty> = mutableListOf()
        if(properties and BluetoothGattCharacteristic.PROPERTY_READ != 0) {
            propertiesList.add(CharacteristicProperty.READ)
        }
        if(properties and BluetoothGattCharacteristic.PROPERTY_WRITE != 0) {
            propertiesList.add(CharacteristicProperty.WRITE)
        }
        if(properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0) {
            propertiesList.add(CharacteristicProperty.NOTIFY)
        }
        return propertiesList
    }
}