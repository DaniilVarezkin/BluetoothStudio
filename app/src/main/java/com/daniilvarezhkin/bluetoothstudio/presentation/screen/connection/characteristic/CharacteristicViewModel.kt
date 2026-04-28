package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.DeviceConnection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.WriteType
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.GattCharacteristic
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.command.Command
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.usecase.GetOrCreateDeviceManagerUseCase
import com.daniilvarezhkin.core.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import timber.log.Timber

@HiltViewModel(assistedFactory = CharacteristicViewModel.Factory::class)
class CharacteristicViewModel @AssistedInject constructor(
    @Assisted private val charKey: CharacteristicKey,
    private val getOrCreateDeviceManagerUseCase: GetOrCreateDeviceManagerUseCase,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(charKey: CharacteristicKey): CharacteristicViewModel
    }

    private val deviceConnection = MutableStateFlow<DeviceConnection?>(null)
    private var characteristic: MutableStateFlow<GattCharacteristic?> = MutableStateFlow(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = deviceConnection.flatMapLatest { device ->
        if(device == null) {
            flowOf(CharacteristicUiState())
        } else {
            combine(
                device.characteristics.map { it[charKey] }.distinctUntilChanged(),
                device.charValues.map { it[charKey] }.distinctUntilChanged()
            ) { char, value ->
                CharacteristicUiState(
                    characteristic = char,
                    value = value
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CharacteristicUiState()
    )

    init {
        Timber.v("Открыт экран настроек характеристики: ${charKey.uuid}")
        setupConnection()
    }

    private fun setupConnection() {
        when (val result = getOrCreateDeviceManagerUseCase(charKey.serviceKey.address)) {
            is Result.Success -> {
                val manager = result.data
                deviceConnection.update { manager }
            }

            is Result.Error -> {
                Timber.e("DeviceManager creation error: ${result.error}")
            }
        }
    }

    fun readCharacteristic() {
        Timber.i("Reading characteristic: ${charKey.uuid}")
        deviceConnection.value?.sendCommand(Command.Read(charKey, onGetResult = { result ->
            Timber.i("The result of reading is received")
            result.onSuccess { Timber.d("Успех: $it") }
            result.onError { Timber.w("Неудача") } //TODO переделать
        }))
    }

    fun writeCharacteristic(value: ByteArray) {
        Timber.i("Writing a value to a characteristic: ${charKey.uuid}, value: ${value.decodeToString()}")
        deviceConnection.value?.sendCommand(Command.Write(
            charKey = charKey,
            value = value,
            writeType = WriteType.NO_RESPONSE
        ))
    }
}