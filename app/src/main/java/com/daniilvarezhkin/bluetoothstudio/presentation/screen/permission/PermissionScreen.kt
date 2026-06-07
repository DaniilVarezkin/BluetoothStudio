package com.daniilvarezhkin.bluetoothstudio.presentation.screen.permission

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import timber.log.Timber


@Composable
fun PermissionScreen(
    modifier: Modifier = Modifier,
    onPermissionsGranted: () -> Unit
) {
    val vm: PermissionScreenViewModel = hiltViewModel()

    PermissionScreenContent(
        modifier = modifier,
        notGrantedPermissions = vm.getNotGrantedPermissions(),
        onPermissionsGranted = onPermissionsGranted
    )
}

@Composable
fun PermissionScreenContent(
    modifier: Modifier = Modifier,
    notGrantedPermissions: Array<String>,
    onPermissionsGranted: () -> Unit
) {
    var isPermissionDenied by remember {
        mutableStateOf(false)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        if (result.values.all { it }) {
            Timber.i("Все разрешения выданы")
            onPermissionsGranted()
        } else {
            Timber.w("Не все разрешения были выданы")
            isPermissionDenied = true
        }
    }

    LaunchedEffect(notGrantedPermissions.contentHashCode()) {
        if (notGrantedPermissions.isEmpty()) {
            Timber.i("Все разрешения уже выданы")
            onPermissionsGranted()
        }
    }

    if (notGrantedPermissions.isEmpty()) return

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Нужны разрешения",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Приложению необходим доступ к Bluetooth, чтобы находить BLE-устройства, подключаться к ним и работать с их характеристиками.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "После выдачи разрешений приложение сможет:",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = """
                            Искать доступные BLE-устройства
                            
                            Подключаться к выбранному устройству
                            
                            Читать и записывать характеристики
                        """.trimIndent(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            if (isPermissionDenied) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Без этих разрешений приложение не сможет работать с BLE-устройствами.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    launcher.launch(notGrantedPermissions)
                }
            ) {
                Text(
                    text = if (isPermissionDenied) {
                        "Попробовать снова"
                    } else {
                        "Выдать разрешения"
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, widthDp = 310)
fun PermissionScreenContentPreview(){
    PermissionScreenContent(
        notGrantedPermissions = arrayOf(""),
        onPermissionsGranted = {}
    )
}