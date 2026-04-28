package com.daniilvarezhkin.bluetoothstudio.presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniilvarezhkin.bluetoothstudio.domain.settings.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.observeShowTimestamp().collect { settingValue ->
                _uiState.update { it.copy(showTimestampInLog = settingValue) }
            }
        }
    }

    fun changeShowTimestampSetting(value: Boolean) {
        viewModelScope.launch {
            settingsRepository.setShowTimestamp(value)
        }
    }
}