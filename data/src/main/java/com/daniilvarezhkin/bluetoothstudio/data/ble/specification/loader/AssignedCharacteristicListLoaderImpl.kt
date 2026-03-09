package com.daniilvarezhkin.bluetoothstudio.data.ble.specification.loader

import android.content.Context
import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.loader.AssignedCharacteristicListLoader
import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.model.characteristic.AssignedCharacteristicList
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AssignedCharacteristicListLoaderImpl(
    private val context: Context
) : AssignedCharacteristicListLoader {

    override suspend fun load(): AssignedCharacteristicList = withContext(Dispatchers.IO) {
        val mapper = ObjectMapper(YAMLFactory())
            .registerKotlinModule()

        context.assets.open("specification/bluetooth_sig/assigned_numbers/uuids/characteristic_uuids.yaml")
            .use { inputStream ->
                mapper.readValue<AssignedCharacteristicList>(inputStream)
            }
    }
}