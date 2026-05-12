package com.daniilvarezhkin.bluetoothstudio.presentation.snackbar

import androidx.compose.material3.SnackbarHostState

class SnackBarController(
    val state: SnackbarHostState
) {
    suspend fun show(visuals: AppSnackbarVisuals) {
        state.showSnackbar(
            visuals = visuals
        )
    }
}