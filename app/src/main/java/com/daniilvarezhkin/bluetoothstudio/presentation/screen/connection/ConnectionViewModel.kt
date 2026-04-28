package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.DeviceConnection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.ConnectionState
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.command.Command
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.usecase.GetOrCreateDeviceManagerUseCase
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogRepository
import com.daniilvarezhkin.core.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel(assistedFactory = ConnectionViewModel.Factory::class)
class ConnectionViewModel @AssistedInject constructor(
    private val getOrCreateBleConnectionUseCase: GetOrCreateDeviceManagerUseCase,
    private val logRepository: LogRepository, //TODO вынести в usecase
    @Assisted private val address: String
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(address: String): ConnectionViewModel
    }

    private var deviceConnectionFlow = MutableStateFlow<DeviceConnection?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<ConnectionUiState> = deviceConnectionFlow.flatMapLatest { deviceManager ->
        if (deviceManager == null) {
            flowOf(ConnectionUiState(address = address))
        } else {
            combine(
                deviceManager.connectionState,
                deviceManager.services.map { it.values.toList() }.distinctUntilChanged(),
                deviceManager.characteristics.map { it.values.toList() }.distinctUntilChanged(),
                deviceManager.charValues
            ) { state, services, chars, valuesMap ->
                ConnectionUiState(
                    address = address,
                    connectionState = state,
                    name = deviceManager.deviceName,
                    services = services,
                    characteristics = chars,
                    values = valuesMap
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ConnectionUiState(
            address = address,
            connectionState = ConnectionState.CONNECTING
        )
    )


    init {
        setupConnection()
    }

    fun readCharacteristic(charKey: CharacteristicKey) {
        viewModelScope.launch {
            deviceConnectionFlow.value?.sendCommand(Command.Read(charKey, onGetResult = { result ->
                Timber.d("Получен результат команды")
                result.onSuccess { Timber.d("Успех: $it") }
                result.onError { Timber.w("Неудача") }
            }))
        }
    }

    private fun setupConnection() {
        when (val result = getOrCreateBleConnectionUseCase(address)) {
            is Result.Success -> {
                val conn = result.data
                deviceConnectionFlow.update { conn }
                conn.connect()
            }

            is Result.Error -> {
                Timber.e("DeviceManager creation error: ${result.error}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("onCleared: ${this::class.simpleName}")
    }
}