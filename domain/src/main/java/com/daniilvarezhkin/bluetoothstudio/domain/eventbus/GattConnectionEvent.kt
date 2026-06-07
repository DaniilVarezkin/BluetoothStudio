package com.daniilvarezhkin.bluetoothstudio.domain.eventbus

sealed interface GattConnectionEvent : Event {
    data class Error(val address: String, val error: String) : GattConnectionEvent
    data class Success(val address: String, val error: String) : GattConnectionEvent
}