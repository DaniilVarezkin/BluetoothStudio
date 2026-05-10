package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.ConnectionUiState
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.ConnectionViewModel
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.TabItem.NOTIFY
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.TabItem.READ
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.TabItem.WRITE
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.read.ReadCharContent
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.write.WriteCharContent
import com.daniilvarezhkin.bluetoothstudio.presentation.ui.theme.Typography
import com.daniilvarezhkin.bluetoothstudio.presentation.views.connections.ConnectionTopBar
import com.daniilvarezhkin.bluetoothstudio.presentation.views.navigation.CharacteristicActionBar

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
    val availableActions = remember(charUiState.characteristic?.properties) {
        filterByCharProperties(charUiState.characteristic?.properties ?: emptyList())
    }

    var selectedAction by rememberSaveable {
        mutableStateOf(TabItem.READ)
    }

    LaunchedEffect(availableActions) {
        if (availableActions.isNotEmpty() && selectedAction !in availableActions) {
            selectedAction = availableActions.first()
        }
    }

    val uuidText = charUiState.characteristic?.shortUuid
        ?: charUiState.characteristic?.uuid?.toString()?.uppercase()
        ?: "—"

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
            Text(
                text = charUiState.characteristic?.name
                    ?: stringResource(R.string.custom_characteristic),
                style = Typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (availableActions.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_char_actions),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                CharacteristicActionBar(
                    selectedAction = selectedAction,
                    onSelectAction = { selectedAction = it },
                    actions = availableActions,
                    uuid = uuidText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 8.dp)
                )

                when (selectedAction) {
                    READ -> {
                        ReadCharContent(
                            value = charUiState.value,
                            onReadChar = onReadChar,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    WRITE -> {
                        WriteCharContent(
                            onWriteChar = onWriteChar,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    NOTIFY -> {
                        Text("Notify content")
                    }
                }
            }
        }
    }
}