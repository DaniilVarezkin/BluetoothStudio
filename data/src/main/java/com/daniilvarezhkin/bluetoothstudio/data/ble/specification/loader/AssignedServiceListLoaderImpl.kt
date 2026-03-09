package com.daniilvarezhkin.bluetoothstudio.data.ble.specification.loader

import android.content.Context
import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.loader.AssignedServiceListLoader
import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.model.service.AssignedServiceList
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AssignedServiceListLoaderImpl(private val context: Context) : AssignedServiceListLoader {

    override suspend fun load(): AssignedServiceList = withContext(Dispatchers.IO) {
        val mapper = ObjectMapper(YAMLFactory())
            .registerKotlinModule()

        context.assets.open("specification/bluetooth_sig/assigned_numbers/uuids/service_uuids.yaml").use { inputStream ->
            mapper.readValue<AssignedServiceList>(inputStream)
        }
    }
}

