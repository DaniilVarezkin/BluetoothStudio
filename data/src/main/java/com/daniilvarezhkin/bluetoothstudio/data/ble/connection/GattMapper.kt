package com.daniilvarezhkin.bluetoothstudio.data.ble.connection

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.CharProperty
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattDescriptor
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.ServiceKey
import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.repository.AssignedCharacteristicRepository
import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.repository.AssignedServiceRepository

class GattMapper(
    private val assignedServiceRepository: AssignedServiceRepository,
    private val assignedCharacteristicRepository: AssignedCharacteristicRepository
) {
    fun mapRawServices(address: String, services: List<BluetoothGattService>): GattTableSnapshot {
        val serviceMap = mutableMapOf<ServiceKey, GattService>()
        val characteristicMap = mutableMapOf<CharacteristicKey, GattCharacteristic>()

        services.forEach { service ->
            val serviceKey = service.createKey(address)
            serviceMap[serviceKey] = service.toGattService(serviceKey)

            service.characteristics.forEach { char ->
                val charKey = char.createKey(address)
                characteristicMap[charKey] = char.toGattCharacteristic(charKey)
            }
        }
        return GattTableSnapshot(
            services = serviceMap,
            characteristics = characteristicMap
        )
    }

    private fun BluetoothGattService.toGattService(key: ServiceKey): GattService {
        val assignedService = assignedServiceRepository.getByUuid(this.uuid)
        return GattService(
            key = key,
            uuid = this.uuid,
            shortUuid = assignedService?.uuid,
            name = assignedService?.name,
            characteristics = characteristics.map { it.toGattCharacteristic(it.createKey(key)) }
        )
    }

    private fun BluetoothGattCharacteristic.toGattCharacteristic(key: CharacteristicKey): GattCharacteristic {
        val assignedCharacteristic = assignedCharacteristicRepository.getByUuid(this.uuid)
        return GattCharacteristic(
            key = key,
            uuid = this.uuid,
            shortUuid = assignedCharacteristic?.uuid,
            name = assignedCharacteristic?.name,
            serviceUuid = this.service.uuid,
            properties = decodeProperties(this.properties)
        )
    }

    private fun BluetoothGattDescriptor.toGattDescriptor(): GattDescriptor {
        return GattDescriptor(
            uuid = uuid
        )
    }

    private fun decodeProperties(properties: Int): List<CharProperty> {
        val propertiesList: MutableList<CharProperty> = mutableListOf()
        if(properties and BluetoothGattCharacteristic.PROPERTY_READ != 0) {
            propertiesList.add(CharProperty.READ)
        }
        if(properties and BluetoothGattCharacteristic.PROPERTY_WRITE != 0) {
            propertiesList.add(CharProperty.WRITE)
        }
        if(properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0) {
            propertiesList.add(CharProperty.NOTIFY)
        }
        return propertiesList
    }
}