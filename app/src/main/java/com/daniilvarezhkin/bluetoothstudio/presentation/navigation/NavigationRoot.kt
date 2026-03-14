package com.daniilvarezhkin.bluetoothstudio.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.daniilvarezhkin.bluetoothstudio.data.ble.permissions.BLUETOOTH_PERMISSIONS
import com.daniilvarezhkin.bluetoothstudio.data.ble.permissions.PermissionManager
import com.daniilvarezhkin.bluetoothstudio.presentation.navigation.connection.ConnectionNavigation
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.shared.ConnectionViewModel
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.permission.PermissionScreen
import com.daniilvarezhkin.bluetoothstudio.presentation.screen.scan.ScanScreen
import com.daniilvarezhkin.bluetoothstudio.presentation.views.BottomNavigation

@Composable
fun NavigationRoot(
    permissionManager: PermissionManager,
    modifier: Modifier = Modifier
) {
    val startRoute = remember {
        if (permissionManager.getNotGranted(BLUETOOTH_PERMISSIONS).isEmpty()) {
            Route.Devices
        } else {
            Route.PermissionGate
        }
    }

    val navigationState = rememberNavigationState(startRoute, TOP_LEVEL_DESTINATIONS.keys)
    val navigator = Navigator(navigationState)

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavigation(
                selectedKey = navigationState.topLevelRoute,
                onSelectKey = navigator::goTo
            )
        }
    ) { innerPadding ->

        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            onBack = { navigator.goBack() },
            entries = navigationState.toEntries(
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator(),
                ),
                entryProvider = entryProvider {
                    entry<Route.PermissionGate> {
                        PermissionScreen(onGrandPermissions = { navigator.goTo(Route.Devices) })
                    }

                    entry<Route.Devices> {
                        ScanScreen(
                            viewModel = hiltViewModel(),
                            onDeviceClick = { macAddress ->
                                navigator.goTo(Route.Connection(macAddress))
                            }
                        )
                    }

                    entry<Route.Connections> { key ->
                        Box(
                            modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Подключения")
                        }
                    }

                    entry<Route.Settings> { key ->
                        Box(
                            modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Настройки")
                        }
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
        )
    }
}