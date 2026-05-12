package com.daniilvarezhkin.bluetoothstudio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.daniilvarezhkin.bluetoothstudio.data.ble.permissions.PermissionManager
import com.daniilvarezhkin.bluetoothstudio.domain.eventbus.EventBus
import com.daniilvarezhkin.bluetoothstudio.presentation.navigation.NavigationRoot
import com.daniilvarezhkin.bluetoothstudio.presentation.ui.theme.BluetoothStudioTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var permissionManager: PermissionManager

    @Inject
    lateinit var eventBus: EventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BluetoothStudioTheme {
                NavigationRoot(
                    permissionManager,
                    rootViewModel = hiltViewModel()
                )
            }
        }
    }
}