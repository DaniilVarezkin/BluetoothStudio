package com.daniilvarezhkin.bluetoothstudio.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniilvarezhkin.bluetoothstudio.domain.eventbus.DeviceConnectionEvent
import com.daniilvarezhkin.bluetoothstudio.domain.eventbus.Event
import com.daniilvarezhkin.bluetoothstudio.domain.eventbus.EventBus
import com.daniilvarezhkin.bluetoothstudio.presentation.event.UiEvent
import com.daniilvarezhkin.bluetoothstudio.presentation.snackbar.AppSnackbarVisuals
import com.daniilvarezhkin.bluetoothstudio.presentation.snackbar.SnackbarType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class RootViewModel @Inject constructor(
    private val eventBus: EventBus
) : ViewModel() {

    private val _snackbars = MutableSharedFlow<AppSnackbarVisuals>()
    val snackbars = _snackbars.asSharedFlow()

    init {
        viewModelScope.launch {
            eventBus.events.collect { event ->
                handleEvent(event)
            }
        }
    }

    // TODO Подумать над отдельными хандлерами
    private suspend fun handleEvent(event: Event) {
        Timber.i("Получено событие ${event.javaClass.simpleName}")
        when (event) {
            // TODo может сделать событие содержаещее мак адресс
            is UiEvent.ShowSnackbar -> _snackbars.emit(
                event.appSnackbarVisuals
            )

            is DeviceConnectionEvent.Connected -> _snackbars.emit(
                AppSnackbarVisuals(
                    "[${event.address}] Устройство подключено",
                    SnackbarType.Success
                )
            )

            is DeviceConnectionEvent.Disconnected -> _snackbars.emit(
                AppSnackbarVisuals(
                    "[${event.address}] Устройство отключено",
                    SnackbarType.Default
                )
            )

            is DeviceConnectionEvent.Error -> _snackbars.emit(
                AppSnackbarVisuals(
                    "[${event.address}] Произошла ошибка: ${event.error}",
                    SnackbarType.Error
                )
            )

            else -> Timber.w("Событие ${event.javaClass.simpleName} не обработано")
        }
    }
}