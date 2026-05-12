package com.daniilvarezhkin.bluetoothstudio.domain.eventbus

sealed interface DeviceConnectionEvent : Event {
    data class Error(val address: String, val error: String) : DeviceConnectionEvent
    data class Connected(val address: String) : DeviceConnectionEvent
    data class Disconnected(val address: String) : DeviceConnectionEvent
}