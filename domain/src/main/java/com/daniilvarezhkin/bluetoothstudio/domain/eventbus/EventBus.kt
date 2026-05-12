package com.daniilvarezhkin.bluetoothstudio.domain.eventbus

import kotlinx.coroutines.flow.SharedFlow

interface EventBus {
    val events: SharedFlow<Event>
    suspend fun publish(event: Event)
    fun tryPublish(event: Event): Boolean
}