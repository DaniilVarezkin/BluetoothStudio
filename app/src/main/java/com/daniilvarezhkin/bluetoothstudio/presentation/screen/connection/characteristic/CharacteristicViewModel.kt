package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.DeviceConnection
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.WriteType
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.command.Command
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.command.CommandError
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.model.key.CharacteristicKey
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.usecase.GetOrCreateDeviceManagerUseCase
import com.daniilvarezhkin.bluetoothstudio.domain.eventbus.EventBus
import com.daniilvarezhkin.bluetoothstudio.presentation.event.publishSnackBar
import com.daniilvarezhkin.bluetoothstudio.presentation.snackbar.AppSnackbarVisuals
import com.daniilvarezhkin.bluetoothstudio.presentation.snackbar.SnackbarType
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
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel(assistedFactory = CharacteristicViewModel.Factory::class)
class CharacteristicViewModel @AssistedInject constructor(
    @Assisted private val charKey: CharacteristicKey,
    private val getOrCreateDeviceManagerUseCase: GetOrCreateDeviceManagerUseCase,
    private val eventBus: EventBus,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(charKey: CharacteristicKey): CharacteristicViewModel
    }

    private val deviceConnection = MutableStateFlow<DeviceConnection?>(null)

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
        Timber.i("Чтение характеристики: ${charKey.uuid}")

        val connection = deviceConnection.value
        if (connection == null) {
            handleError(CommandError.NoConnection)
            return
        }

        connection.sendCommand(
            Command.Read(
                charKey = charKey,
                onGetResult = { result ->
                    result.onSuccess {
                        Timber.d("Характеристика успешно прочитана: $it")
                    }

                    result.onError {
                        handleError(it)
                    }
                })
        )
    }

    fun writeCharacteristic(value: ByteArray) {
        Timber.i("Запись в характеристику: ${charKey.uuid}, значение: ${value.decodeToString()}")
        val connection = deviceConnection.value

        if (connection == null) {
            handleError(CommandError.NoConnection)
            return
        }

        connection.sendCommand(Command.Write(
            charKey = charKey,
            value = value,
            writeType = WriteType.NO_RESPONSE,
            onGetResult = { result ->
                result.onSuccess {
                    Timber.d("Значение успешно записано в характеристику: ${charKey.uuid}")

                    showSnackbar(
                        AppSnackbarVisuals(
                            message = "Значение отправлено",
                            type = SnackbarType.Success
                        )
                    )
                }

                result.onError { handleError(it) }
            }
        ))
    }

    private fun handleError(error: CommandError) {
        Timber.w("Ошибка выполнения команды: ${error.javaClass.simpleName}")

        val errorMessage = when (error) {
            CommandError.NoConnection -> "Нет соединения с устройством"
            CommandError.NotSuccess -> "Не удалось выполнить операцию"
            CommandError.Timeout -> "Устройство не ответило вовремя"
        }

        showSnackbar(AppSnackbarVisuals(
            message = errorMessage,
            type = SnackbarType.Error
        ))
    }

    private fun showSnackbar(appSnackbarVisuals: AppSnackbarVisuals) {
        viewModelScope.launch {
            eventBus.publishSnackBar(appSnackbarVisuals)
        }
    }
}