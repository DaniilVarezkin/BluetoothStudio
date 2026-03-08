package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.ConnectionState
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattService
import com.daniilvarezhkin.bluetoothstudio.presentation.views.ConnectionTextStatusView
import com.daniilvarezhkin.bluetoothstudio.presentation.views.GattServiceListView
import java.util.UUID

@Composable
fun DeviceConnectionScreen(
    viewModel: DeviceConnectionViewModel,
    onClickBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DeviceConnectionScreenContent(
        uiState,
        onClickBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceConnectionScreenContent(
    uiState: DeviceConnectionUiState,
    onClickBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "",
                        )
                    }
                },
                title = { Text(text = uiState.deviceName ?: stringResource(R.string.unknown_name)) },
                actions = {
                    ConnectionTextStatusView(
                        status = uiState.connectionState,
                        modifier = Modifier.padding(end = 16.dp) //TODO убрать хардкор
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text("${stringResource(R.string.address)}: ${uiState.macAddress}")
            GattServiceListView(
                services = uiState.services,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
@Composable
@Preview(showBackground = true)
fun DeviceConnectionScreenContentPreview() {
    DeviceConnectionScreenContent(
        DeviceConnectionUiState(
            macAddress = "macAdress",
            connectionState = ConnectionState.CONNECTED,
            deviceName = "Device Name",
            services = listOf(
                GattService(
                    uuid = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb"),,
                    includedServices = emptyList(),
                    characteristics = listOf(
                        GattCharacteristic(UUID.fromString("00002a00-0000-1000-8000-00805f9b34fb")),
                        GattCharacteristic(UUID.fromString("00002a01-0000-1000-8000-00805f9b34fb"))
                    )
                ),
                GattService(
                    uuid = UUID.fromString("00001801-0000-1000-8000-00805f9b34fb"),,
                    includedServices = emptyList(),
                    characteristics = listOf(
                        GattCharacteristic(UUID.fromString("00002a05-0000-1000-8000-00805f9b34fb"))
                    )
                ),
                GattService(
                    uuid = UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb"),,
                    includedServices = emptyList(),
                    characteristics = listOf(
                        GattCharacteristic(UUID.fromString("00002a29-0000-1000-8000-00805f9b34fb")),
                        GattCharacteristic(UUID.fromString("00002a24-0000-1000-8000-00805f9b34fb"))
                    )
                ),
                GattService(
                    uuid = UUID.fromString("d0611e78-bbb4-4591-a5f8-487910ae4366"),,
                    includedServices = emptyList(),
                    characteristics = listOf(
                        GattCharacteristic(UUID.fromString("8667556c-9a37-4c91-84ed-54ee27d90049"))
                    )
                ),
                GattService(
                    uuid = UUID.fromString("9fa480e0-4967-4542-9390-d343dc5d04ae"),,
                    includedServices = emptyList(),
                    characteristics = listOf(
                        GattCharacteristic(UUID.fromString("af0badb1-5b99-43cd-917a-a77bc549e3cc"))
                    )
                ),
                GattService(
                    uuid = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb"),,
                    includedServices = emptyList(),
                    characteristics = listOf(
                        GattCharacteristic(UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb"))
                    )
                ),
                GattService(
                    uuid = UUID.fromString("00001805-0000-1000-8000-00805f9b34fb"),,
                    includedServices = emptyList(),
                    characteristics = listOf(
                        GattCharacteristic(UUID.fromString("00002a2b-0000-1000-8000-00805f9b34fb")),
                        GattCharacteristic(UUID.fromString("00002a0f-0000-1000-8000-00805f9b34fb"))
                    )
                ),
                GattService(
                    uuid = UUID.fromString("7905f431-b5ce-4e99-a40f-4b1e122d00d0"),,
                    includedServices = emptyList(),
                    characteristics = listOf(
                        GattCharacteristic(UUID.fromString("69d1d8f3-45e1-49a8-9821-9bbdfdaad9d9")),
                        GattCharacteristic(UUID.fromString("9fbf120d-6301-42d9-8c58-25e699a21dbd")),
                        GattCharacteristic(UUID.fromString("22eac6e9-24d6-4bb5-be44-b36ace7c7bfb"))
                    )
                ),
                GattService(
                    uuid = UUID.fromString("89d3502b-0f36-433a-8ef4-c502ad55f8dc"),,
                    includedServices = emptyList(),
                    characteristics = listOf(
                        GattCharacteristic(UUID.fromString("9b3c81d8-57b1-4a8a-b8df-0e56f7ca51c2")),
                        GattCharacteristic(UUID.fromString("2f7cabce-808d-411f-9a0c-bb92ba96c102")),
                        GattCharacteristic(UUID.fromString("c6b2f38c-23ab-46d8-a6ab-a3a870bbd5d7"))
                    )
                )
            )
        ),
        {}
    )
}