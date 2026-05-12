package com.daniilvarezhkin.bluetoothstudio.presentation.views.snackbar

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.daniilvarezhkin.bluetoothstudio.presentation.snackbar.AppSnackbarVisuals
import com.daniilvarezhkin.bluetoothstudio.presentation.snackbar.SnackbarType
import com.daniilvarezhkin.bluetoothstudio.presentation.ui.theme.Connected
import com.daniilvarezhkin.bluetoothstudio.presentation.ui.theme.Warning

@Composable
fun getContainerColor(snackbarType: SnackbarType) = when (snackbarType) {
    SnackbarType.Default -> SnackbarDefaults.color
    SnackbarType.Success -> Connected
    SnackbarType.Error -> MaterialTheme.colorScheme.errorContainer
    SnackbarType.Warning -> Warning
}

@Composable
fun getContentColor(snackbarType: SnackbarType) = when (snackbarType) {
    SnackbarType.Default -> SnackbarDefaults.contentColor
    SnackbarType.Success -> SnackbarDefaults.contentColor
    SnackbarType.Error -> MaterialTheme.colorScheme.onErrorContainer
    SnackbarType.Warning -> SnackbarDefaults.contentColor
}

@Composable
fun AppSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier
) {
    val visuals = snackbarData.visuals as? AppSnackbarVisuals

    Snackbar(
        snackbarData = snackbarData,
        modifier = modifier,
        containerColor = getContainerColor(visuals?.type ?: SnackbarType.Default),
        contentColor = getContentColor(visuals?.type ?: SnackbarType.Default),
    )
}