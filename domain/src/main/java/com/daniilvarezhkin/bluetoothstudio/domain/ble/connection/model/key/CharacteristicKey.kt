@file:UseSerializers(UuidSerializer::class)

package com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key

import com.daniilvarezhkin.core.serializer.UuidSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.UUID


@Serializable
data class CharacteristicKey(
    val serviceKey: ServiceKey,
    val uuid: UUID,
    val instanceId: Int,
)