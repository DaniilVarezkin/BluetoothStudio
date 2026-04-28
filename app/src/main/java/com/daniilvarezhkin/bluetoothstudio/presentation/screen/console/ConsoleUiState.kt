package com.daniilvarezhkin.bluetoothstudio.presentation.screen.console

import com.daniilvarezhkin.bluetoothstudio.domain.log.BLog

data class ConsoleUiState(
    val logs: List<BLog> = emptyList(),
    val settings: ConsoleSettings = ConsoleSettings()
)
