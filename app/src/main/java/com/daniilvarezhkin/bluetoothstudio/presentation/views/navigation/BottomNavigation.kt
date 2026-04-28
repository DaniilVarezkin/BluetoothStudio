package com.daniilvarezhkin.bluetoothstudio.presentation.views.navigation

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import com.daniilvarezhkin.bluetoothstudio.presentation.navigation.Route
import com.daniilvarezhkin.bluetoothstudio.presentation.navigation.TOP_LEVEL_DESTINATIONS

@Composable
fun BottomNavigation(
    selectedKey: NavKey,
    onSelectKey: (NavKey) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier
    ) {
        TOP_LEVEL_DESTINATIONS.forEach { (route, bottomNavItem) ->
            NavigationBarItem(
                selected = route == selectedKey,
                onClick = { onSelectKey(route) },
                icon = {
                    Icon(
                        imageVector = bottomNavItem.icon,
                        contentDescription = stringResource(bottomNavItem.labelRes)
                    )
                },
            )
        }
    }
}

@Composable
@Preview(showBackground = true, widthDp = 300)
fun BottomNavigationPreview() {
    BottomNavigation(
        Route.Connections,
        {}
    )
}