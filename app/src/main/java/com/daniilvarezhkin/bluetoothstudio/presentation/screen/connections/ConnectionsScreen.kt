package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connections

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.presentation.views.connections.ConnectionDeviceItemView

@Composable
fun ConnectionsScreen(
    viewModel: ConnectionsViewModel,
    onNavigateToConnection: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val handleItemClick: (String) -> Unit = remember(uiState.isSelectedState) {
        if (uiState.isSelectedState) {
            viewModel::changeSelectedConnectionState
        } else {
            onNavigateToConnection
        }
    }

    ConnectionsScreenContent(
        uiState = uiState,
        onClickConnection = handleItemClick,
        onLongClick = { address ->
            viewModel.changeSelectedConnectionState(address)
            viewModel.changeSelectedState(!uiState.isSelectedState)
        },
        onClickCloseSelection = { viewModel.changeSelectedState(false) },
        onClickDeleteSelectedConnections = viewModel::deleteSelectedConnections,
        modifier = Modifier
            .fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectionsScreenContent(
    uiState: ConnectionsUiState,
    onClickConnection: (address: String) -> Unit,
    onLongClick: (address: String) -> Unit,
    onClickCloseSelection: () -> Unit,
    onClickDeleteSelectedConnections: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    if (uiState.isSelectedState) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = onClickCloseSelection
                            ) {
                                Icon(Icons.Default.Close, null)
                            }
                            Text(
                                text = uiState.selectedAddresses.count().toString()
                            )
                        }
                    } else {
                        Text("Активные подключения")
                    }
                },
                actions = {
                    if (uiState.isSelectedState) {
                        IconButton(
                            onClick = onClickDeleteSelectedConnections,
                            modifier = Modifier.padding(end = dimensionResource(R.dimen.horizontal_screen_padding))
                        ) {
                            Icon(Icons.Default.Delete, null)
                        }
                    }
                }
            )
        }
    ) { innerPaddings ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
                .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding)),
        ) {
            items(uiState.connections, key = { it.address }) { connection ->
                val connectionState by connection.connectionState.collectAsStateWithLifecycle()

                ConnectionDeviceItemView(
                    selected = uiState.selectedAddresses.contains(connection.address),
                    deviceName = connection.deviceName ?: stringResource(R.string.unknown_name),
                    address = connection.address,
                    onClick = onClickConnection,
                    onLongClick = onLongClick,
                    connectionState = connectionState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                )
            }
        }
    }
}