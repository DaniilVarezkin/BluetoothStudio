package com.daniilvarezhkin.bluetoothstudio.presentation.screen.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import timber.log.Timber


@Composable
fun PermissionScreen(
    modifier: Modifier = Modifier,
    onGrandPermissions: () -> Unit
) {
    val vm: PermissionScreenViewModel = hiltViewModel()

    PermissionScreenContent(
        modifier = modifier,
        notGrantedPermissions = vm.getNotGrantedPermissions(),
        onGrandPermissions = onGrandPermissions
    )
}

@Composable
fun PermissionScreenContent(
    modifier: Modifier = Modifier,
    notGrantedPermissions: Array<String>,
    onGrandPermissions: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        if (result.values.all { it }) {
            Timber.i("Все разрешения выданы")
            onGrandPermissions()
        }
    }

    if (notGrantedPermissions.isEmpty()) {
        Timber.i("Все разрешения уже выданы")
        onGrandPermissions()
        return
    } else {
        LaunchedEffect(Unit) {
            launcher.launch(notGrantedPermissions)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                launcher.launch(notGrantedPermissions)
            }
        ) {
            Text("Выдать разрешения")
        }
    }
}