package com.daniilvarezhkin.bluetoothstudio.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.daniilvarezhkin.bluetoothstudio.data.ble.permissions.BLUETOOTH_PERMISSIONS
import com.daniilvarezhkin.bluetoothstudio.data.ble.permissions.PermissionManager
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.DeviceConnectionScreen
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.DeviceConnectionViewModel
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

    val backStack = rememberNavBackStack(startRoute)

    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = { key ->
            when (key) {
                is Route.PermissionGate -> {
                    NavEntry(key) {
                        PermissionScreen(onGrandPermissions = { backStack.add(Route.DeviceList) })
                    }
                }

                is Route.DeviceList -> {
                    NavEntry(key) {
                        ScanScreen(
                            viewModel = hiltViewModel(),
                            onDeviceClick = { backStack.add(Route.DeviceConnection(it)) }
                        )
                    }
                }

                is Route.DeviceConnection -> {
                    NavEntry(key) {
                        DeviceConnectionScreen(
                            viewModel = hiltViewModel(
                                creationCallback = { factory: DeviceConnectionViewModel.Factory ->
                                    factory.create(key.macAddress)
                                }
                            ),
                            onClickBack = { backStack.removeAt(backStack.lastIndex) }
                        )
                    }
                }

                else -> error("Unknown NavKey: $key")
            }
        }
    )
}