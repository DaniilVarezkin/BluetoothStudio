package com.daniilvarezhkin.bluetoothstudio.presentation.screen.console

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daniilvarezhkin.bluetoothstudio.R
import com.daniilvarezhkin.bluetoothstudio.presentation.views.console.log.LogListView

@Composable
fun ConsoleScreen(
    viewModel: ConsoleViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ConsoleScreenContent(
        uiState = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsoleScreenContent(
    uiState: ConsoleUiState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.nav_item_console))
                },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings),
        ) {
            Text(
                "Фильтры",
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding))
            )
            LogListView(
                BLogs = uiState.logs,
                showTimestamp = uiState.settings.showTimestamp,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}