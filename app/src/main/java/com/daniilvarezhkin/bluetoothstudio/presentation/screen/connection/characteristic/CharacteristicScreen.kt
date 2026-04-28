package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.read.ReadCharContent
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.ConnectionUiState
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.ConnectionViewModel
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.TabItem.*
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.log.CharLogsContent
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.write.WriteCharContent
import com.daniilvarezhkin.bluetoothstudio.presentation.ui.theme.Typography
import com.daniilvarezhkin.bluetoothstudio.presentation.views.navigation.CharacteristicTabRow
import com.daniilvarezhkin.bluetoothstudio.presentation.views.connections.ConnectionTopBar

@Composable
fun CharacteristicScreen(
    connectionViewModel: ConnectionViewModel,
    charViewModel: CharacteristicViewModel,
) {
    val connectionUiState by connectionViewModel.uiState.collectAsStateWithLifecycle()
    val charUiState by charViewModel.uiState.collectAsStateWithLifecycle()

    CharacteristicScreenContent(
        connectionUiState = connectionUiState,
        charUiState = charUiState,
        onReadChar = charViewModel::readCharacteristic,
        onWriteChar = charViewModel::writeCharacteristic
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacteristicScreenContent(
    connectionUiState: ConnectionUiState,
    charUiState: CharacteristicUiState,
    onReadChar: () -> Unit,
    onWriteChar: (ByteArray) -> Unit,

    ) {
    var selectedTab by remember { mutableStateOf(TabItem.READ) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                ConnectionTopBar(
                    deviceName = connectionUiState.name,
                    address = connectionUiState.address,
                    connectionState = connectionUiState.connectionState
                )
                Column(
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(R.dimen.horizontal_screen_padding),
                    )
                ) {
                    // Имя характеристики
                    Text(
                        text = charUiState.characteristic?.name
                            ?: stringResource(R.string.custom_characteristic),
                        style = Typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    // UUID характеристики с иконкой копирования
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "UUID: ${
                                charUiState.characteristic?.shortUuid
                                    ?: charUiState.characteristic?.uuid.toString().uppercase()
                            }",
                            style = Typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontFamily = FontFamily.Monospace,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // TabRow с отступами
                CharacteristicTabRow(
                    tabItems = filterByCharProperties(charUiState.characteristic?.properties ?: emptyList()),
                    selectedTab = selectedTab,
                    onSelectTab = { selectedTab = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding))
                        .padding(top = 12.dp, bottom = 8.dp)
                )
            }
        }
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
                .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding)),
            verticalArrangement = Arrangement.Top
        ) {
            when (selectedTab) {
                READ -> {
                    ReadCharContent(
                        value = charUiState.value,
                        onReadChar = onReadChar,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                WRITE -> {
                    WriteCharContent(
                        onWriteChar = onWriteChar,
                        modifier = Modifier
                            .fillMaxSize()

                    )
                }

                NOTIFY -> {
                    Text("INDICATE")
                }
            }
        }
    }
}