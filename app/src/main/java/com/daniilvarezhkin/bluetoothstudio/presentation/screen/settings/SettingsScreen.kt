package com.daniilvarezhkin.bluetoothstudio.presentation.screen.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.daniilvarezhkin.bluetoothstudio.presentation.views.settings.SwitchSettingItem

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SettingsScreenContent(
        uiState = uiState,
        changeShowTimestampSetting = viewModel::changeShowTimestampSetting
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    uiState: SettingsUiState,
    changeShowTimestampSetting: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Настройки")
                }
            )
        }
    ) { innerPaddings ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
                .padding(horizontal = dimensionResource(R.dimen.horizontal_screen_padding))
        ) {
            item {
                SwitchSettingItem(
                    title = stringResource(R.string.setting_show_timestamp_console),
                    subtitle = if (uiState.showTimestampInLog) "Включено" else "Выключено",
                    checked = uiState.showTimestampInLog,
                    onCheckedChange = changeShowTimestampSetting
                )
            }
        }
    }
}