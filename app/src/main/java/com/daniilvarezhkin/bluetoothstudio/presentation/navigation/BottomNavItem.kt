package com.daniilvarezhkin.bluetoothstudio.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.daniilvarezhkin.bluetoothstudio.R

data class BottomNavItem(
    val labelRes: Int,
    val icon: ImageVector
)

//TODO придумать без хардкора
val TOP_LEVEL_DESTINATIONS = mapOf(
    Route.Devices to BottomNavItem(
        R.string.nav_item_devices,
        Icons.AutoMirrored.Filled.List
    ),
    Route.Connections to BottomNavItem(
        R.string.nav_item_connections,
        Icons.Filled.AccountBox
    ),
    Route.Settings to BottomNavItem(
        R.string.nav_item_settings,
        Icons.Filled.Settings
    ),
)