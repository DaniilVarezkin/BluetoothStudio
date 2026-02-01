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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.daniilvarezhkin.bluetoothstudio.data.ble.permissions.BLUETOOTH_PERMISSIONS
import com.daniilvarezhkin.bluetoothstudio.data.ble.permissions.PermissionManager
import com.daniilvarezhkin.bluetoothstudio.ui.theme.BluetoothStudioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BluetoothStudioTheme {
                val permissionManager = PermissionManager(LocalContext.current)

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions()
                ) { result ->
                    Log.e("AAA", result.toString())
                    if(result.values.all { it }) {
                        Log.e("AAA", "Разрешения выданы")
                    }
                }

                Log.e("AAA", permissionManager.getNotGranted(BLUETOOTH_PERMISSIONS).toList().toString())

                if (permissionManager.getNotGranted(BLUETOOTH_PERMISSIONS).isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0, 255, 0))
                    )
                } else {
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