package com.daniilvarezhkin.bluetoothstudio.presentation.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

data class AppSnackbarVisuals(
    override val message: String,
    val type: SnackbarType = SnackbarType.Default,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
    override val withDismissAction: Boolean = false,
) : SnackbarVisuals