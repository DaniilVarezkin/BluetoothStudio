@file:UseSerializers(UuidSerializer::class)

package com.daniilvarezhkin.bluetoothstudio.presentation.navigation

import kotlinx.serialization.UseSerializers
import androidx.navigation3.runtime.NavKey
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.core.serializer.UuidSerializer
import kotlinx.serialization.Serializable

@Serializable
sealed class Route : NavKey {
    @Serializable
    data object PermissionGate : Route()

    @Serializable
    data object Devices : Route()

    @Serializable
    data object Connections : Route()

    @Serializable
    data object Console : Route()

    @Serializable
    data object Settings : Route()

    @Serializable
    sealed class Connection : Route() {
        @Serializable
        data class General(val address: String) : Connection()

        @Serializable
        data class Characteristic(
            val charKey: CharacteristicKey
        ) : Route()
    }
}

