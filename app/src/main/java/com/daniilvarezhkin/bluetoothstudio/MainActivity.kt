package com.daniilvarezhkin.bluetoothstudio

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.daniilvarezhkin.bluetoothstudio.data.ble.permissions.BLUETOOTH_PERMISSIONS
import com.daniilvarezhkin.bluetoothstudio.data.ble.permissions.PermissionManager
import com.daniilvarezhkin.bluetoothstudio.presentation.scan.ScanScreen
import com.daniilvarezhkin.bluetoothstudio.ui.theme.BluetoothStudioTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BluetoothStudioTheme {
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions()
                ) { result ->
                    if(result.values.all { it }) {

                    }
                }

                if (permissionManager.check(BLUETOOTH_PERMISSIONS)) {
                    ScanScreen()
                } else {
                    LaunchedEffect(Unit) {
                        launcher.launch(permissionManager.getNotGranted(BLUETOOTH_PERMISSIONS))
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(255, 0, 0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                Log.e("AAA", "Click")
                                launcher.launch(permissionManager.getNotGranted(BLUETOOTH_PERMISSIONS))
                            }
                        ) {
                            Text("Выдать разрешения")
                        }
                    }
                }
            }
        }
    }
}