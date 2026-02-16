package com.daniilvarezhkin.bluetoothstudio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.daniilvarezhkin.bluetoothstudio.data.ble.permissions.PermissionManager
import com.daniilvarezhkin.bluetoothstudio.presentation.navigation.NavigationRoot
import com.daniilvarezhkin.bluetoothstudio.ui.theme.BluetoothStudioTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BluetoothStudioTheme {
                Scaffold { innerPadding ->
                    NavigationRoot(
                        modifier = Modifier
                            .padding(innerPadding),
                        permissionManager
                    )
                }
            }
        }
    }
}