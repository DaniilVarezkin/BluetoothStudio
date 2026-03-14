package com.daniilvarezhkin.bluetoothstudio.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object PermissionGate : Route

    @Serializable
    data object Devices : Route

    @Serializable
    data object Connections : Route

    @Serializable
    data object Settings : Route

    @Serializable
    data class Connection(val macAddress: String) : Route {
        @Serializable
        data object General : Route

        @Serializable
        data class Characteristic(val serviceUuid: String, val characteristicUuid: String) : Route
    }
}

