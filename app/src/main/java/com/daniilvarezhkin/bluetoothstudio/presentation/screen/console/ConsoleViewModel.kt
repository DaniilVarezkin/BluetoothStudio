package com.daniilvarezhkin.bluetoothstudio.presentation.screen.console

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniilvarezhkin.bluetoothstudio.domain.log.BLog
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogRepository
import com.daniilvarezhkin.bluetoothstudio.domain.settings.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ConsoleViewModel @Inject constructor(
    logRepository: LogRepository,
    settingsRepository: SettingsRepository,
) : ViewModel() {

    val logFlow = logRepository.logs
        .scan(emptyList<BLog>()) { acc, value ->
            acc + value
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    private val _uiState = MutableStateFlow(ConsoleUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            logFlow.combine(settingsRepository.observeShowTimestamp()) { logs, timestampSetting ->
                _uiState.update {
                    it.copy(
                        logs = logs,
                        settings = it.settings.copy(showTimestamp = timestampSetting)
                    )
                }
            }.collect()
        }
    }
}