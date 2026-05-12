package com.daniilvarezhkin.bluetoothstudio.presentation.event

import com.daniilvarezhkin.bluetoothstudio.domain.eventbus.Event
import com.daniilvarezhkin.bluetoothstudio.presentation.snackbar.AppSnackbarVisuals

sealed interface UiEvent : Event {
    data class ShowSnackbar(
        val appSnackbarVisuals: AppSnackbarVisuals
    ) : UiEvent
}