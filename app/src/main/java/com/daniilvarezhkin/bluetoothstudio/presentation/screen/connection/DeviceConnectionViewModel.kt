package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.BleConnection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.usecase.GetBleConnectionUseCase
import com.daniilvarezhkin.core.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel(assistedFactory = DeviceConnectionViewModel.Factory::class)
class DeviceConnectionViewModel @AssistedInject constructor(
    private val getBleConnectionUseCase: GetBleConnectionUseCase,
    @Assisted private val macAddress: String
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(macAddress: String): DeviceConnectionViewModel
    }

    private var observeConnectionStateJob: Job? = null
    private val _uiState = MutableStateFlow(DeviceConnectionUiState(macAddress = macAddress))
    val uiState = _uiState.asStateFlow()


    private var connection: BleConnection? = null

    init {
        Timber.i("Соединение с macAddress: $macAddress")
        connect()
    }

    private fun connect() {
        when (val result = getBleConnectionUseCase(macAddress)) {
            is Result.Success -> {
                connection = result.data
                observeConnectionState()
                connection?.connect()
            }

            is Result.Error -> {
                Timber.e("Ошибка подключения: ${result.error}")
            }
        }
    }

    private fun observeConnectionState() {
        observeConnectionStateJob?.cancel()
        observeConnectionStateJob = viewModelScope.launch {
            connection?.apply {
                connectionState.collect { state ->
                    _uiState.update {
                        it.copy(connectionState = state)
                    }
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        connection?.disconnect()
        connection = null
    }
}