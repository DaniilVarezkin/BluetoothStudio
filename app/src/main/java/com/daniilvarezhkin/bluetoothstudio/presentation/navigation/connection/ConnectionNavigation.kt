package com.daniilvarezhkin.bluetoothstudio.presentation.navigation.connection

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.daniilvarezhkin.bluetoothstudio.presentation.navigation.Route
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.CharacteristicScreen
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.CharacteristicViewModel
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.general.ConnectionScreen
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.shared.ConnectionViewModel

@Composable
fun ConnectionNavigation(
    connectionViewModel: ConnectionViewModel,
    modifier: Modifier = Modifier
) {
    val connectionBackStack = rememberNavBackStack(Route.Connection.General)

    NavDisplay(
        backStack = connectionBackStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<Route.Connection.General> {
                ConnectionScreen(
                    viewModel = connectionViewModel,
                    onClickBack = { connectionBackStack.removeAt(connectionBackStack.lastIndex) },
                    onClickCharacteristic = { characteristic ->
                        connectionBackStack.add(
                            Route.Connection.Characteristic(
                                characteristic.serviceUuid.toString(),
                                characteristic.uuid.toString()
                            )
                        )
                    }
                )
            }

            entry<Route.Connection.Characteristic> {
                CharacteristicScreen(
                    connectionViewModel = connectionViewModel,
                    characteristicViewModel = viewModel { CharacteristicViewModel() }
                )
            }
        }
    )
}