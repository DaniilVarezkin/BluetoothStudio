package com.daniilvarezhkin.bluetoothstudio.data.eventbus

import com.daniilvarezhkin.bluetoothstudio.domain.eventbus.Event
import com.daniilvarezhkin.bluetoothstudio.domain.eventbus.EventBus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventBusImpl : EventBus {
    private val _events = MutableSharedFlow<Event>(extraBufferCapacity = 10)
    override val events = _events.asSharedFlow()

    override suspend fun publish(event: Event) {
        _events.emit(event)
    }

    override fun tryPublish(event: Event): Boolean {
        return _events.tryEmit(event)
    }
}