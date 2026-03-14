package com.daniilvarezhkin.bluetoothstudio.presentation.navigation

import androidx.navigation3.runtime.NavKey
import timber.log.Timber

class Navigator(
    val state: NavigationState
) {
    fun goTo(key: NavKey) {
        Timber.v("Переход на экран $key")
        if (key in state.backStacks.keys) {
            state.topLevelRoute = key
        } else {
            state.backStacks[state.topLevelRoute]?.add(key)
        }
    }

    fun goBack() {
        val currentBackStack = state.backStacks[state.topLevelRoute]
            ?: error("Back stack for ${state.topLevelRoute} doesn't exist")

        val currentRoute = currentBackStack.last()

        if (currentRoute == state.topLevelRoute) {
            state.topLevelRoute = state.startRoute
        } else {
            currentBackStack.removeLastOrNull()
        }
    }
}