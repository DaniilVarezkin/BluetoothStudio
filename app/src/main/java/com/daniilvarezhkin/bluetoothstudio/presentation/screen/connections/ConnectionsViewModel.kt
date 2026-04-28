package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connections.BleConnectionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ConnectionsViewModel @Inject constructor(
    private val bleConnectionManager: BleConnectionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConnectionsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            bleConnectionManager.managedConnections.collect { connectionsMap ->
                _uiState.update {
                    it.copy(connections = connectionsMap.values.toList())
                }
            }
        }
    }

    /**
     * Меняет состояние экрана для редактирования
     */
    fun changeSelectedState(selectedState: Boolean) {
        _uiState.update {
            it.copy(
                isSelectedState = selectedState,
                selectedAddresses = if (selectedState) it.selectedAddresses else emptySet()
            )
        }
    }

    /**
     * Помечает выделенным элемент по указанному адрессу
     */
    fun changeSelectedConnectionState(address: String) {
        _uiState.update {
            val newSelectedAddresses = if (address in it.selectedAddresses) {
                it.selectedAddresses - address
            } else {
                it.selectedAddresses + address
            }

            it.copy(
                selectedAddresses = newSelectedAddresses,
                isSelectedState = newSelectedAddresses.isNotEmpty()
            )
        }
    }

    fun deleteSelectedConnections() {
        uiState.value.selectedAddresses.forEach { address ->
            bleConnectionManager.removeDeviceConnection(address)
        }

        changeSelectedState(false)
    }
}