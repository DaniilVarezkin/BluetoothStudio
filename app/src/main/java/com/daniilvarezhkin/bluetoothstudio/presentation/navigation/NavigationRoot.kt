package com.daniilvarezhkin.bluetoothstudio.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.daniilvarezhkin.bluetoothstudio.data.ble.permissions.BLUETOOTH_PERMISSIONS
import com.daniilvarezhkin.bluetoothstudio.data.ble.permissions.PermissionManager
import com.daniilvarezhkin.bluetoothstudio.presentation.navigation.connection.ConnectionNavigation
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.shared.ConnectionViewModel
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.permission.PermissionScreen
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.scan.ScanScreen

@Composable
fun NavigationRoot(
    permissionManager: PermissionManager,
    modifier: Modifier = Modifier
) {
    val startRoute = remember {
        if (permissionManager.getNotGranted(BLUETOOTH_PERMISSIONS).isEmpty()) {
            Route.DeviceList
        } else {
            Route.PermissionGate
        }
    }

    val rootBackStack = rememberNavBackStack(startRoute)

    NavDisplay(
        backStack = rootBackStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<Route.PermissionGate> {
                PermissionScreen(onGrandPermissions = { rootBackStack.add(Route.DeviceList) })
            }

            entry<Route.DeviceList> { key ->
                ScanScreen(
                    viewModel = hiltViewModel(),
                    onDeviceClick = { macAddress ->
                        rootBackStack.add(Route.Connection(macAddress))
                    }
                )
            }

            entry<Route.Connection> { key ->
                val connectionViewModel = hiltViewModel(
                    creationCallback = { factory: ConnectionViewModel.Factory ->
                        factory.create(key.macAddress)
                    }
                )
                ConnectionNavigation(
                    connectionViewModel = connectionViewModel,
                )
            }
        }
    )
}