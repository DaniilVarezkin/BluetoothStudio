package com.daniilvarezhkin.bluetoothstudio.presentation.event

import com.daniilvarezhkin.bluetoothstudio.domain.eventbus.EventBus
import com.daniilvarezhkin.bluetoothstudio.presentation.snackbar.AppSnackbarVisuals

suspend fun EventBus.publishSnackBar(appSnackbarVisuals: AppSnackbarVisuals) {
    this.publish(
        UiEvent.ShowSnackbar(appSnackbarVisuals)
    )
}