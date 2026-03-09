package com.daniilvarezhkin.bluetoothstudio.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object PermissionGate : Route, NavKey

    @Serializable
    data object DeviceList : Route, NavKey

    @Serializable
    data class Connection(val macAddress: String) : Route, NavKey {
        @Serializable
        data object General : Route, NavKey

        @Serializable
        data class Characteristic(val serviceUuid: String, val characteristicUuid: String) : Route, NavKey
    }
}