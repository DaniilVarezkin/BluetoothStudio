package com.daniilvarezhkin.bluetoothstudio.presentation.navigation.connection

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.daniilvarezhkin.bluetoothstudio.presentation.navigation.Route
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.CharacteristicScreen
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic.CharacteristicViewModel
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.ConnectionScreen
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.ConnectionViewModel

@Composable
fun ConnectionNavigation(
    startRoute: Route.Connection,
    connectionViewModel: ConnectionViewModel,
    modifier: Modifier = Modifier
) {
    val connectionBackStack = rememberNavBackStack(startRoute)

    NavDisplay(
        backStack = connectionBackStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<Route.Connection.General> { key ->
                ConnectionScreen(
                    viewModel = connectionViewModel,
                    onClickCharacteristic = { char ->
                        connectionBackStack.add(
                            Route.Connection.Characteristic(char.key)
                        )
                    }
                )
            }

            entry<Route.Connection.Characteristic> { key ->

                val characteristicViewModel = hiltViewModel(
                    creationCallback = { factory: CharacteristicViewModel.Factory ->
                        factory.create(key.charKey)
                    }
                )
                CharacteristicScreen(
                    connectionViewModel = connectionViewModel,
                    charViewModel = characteristicViewModel
                )
            }
        }
    )
}