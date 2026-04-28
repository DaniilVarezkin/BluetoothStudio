package com.daniilvarezhkin.bluetoothstudio.presentation.screen.connection.characteristic

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.enums.CharProperty

enum class TabItem(
    val label: String
) {
    READ("Read"),
    WRITE("Write"),
    NOTIFY("Notify"),
}

fun filterByCharProperties(properties: List<CharProperty>): List<TabItem> {
    val list = TabItem.entries.toMutableList()

    if(!properties.contains(CharProperty.READ)) {
        list.remove(TabItem.READ)
    }

    if(!properties.contains(CharProperty.WRITE)) {
        list.remove(TabItem.WRITE)
    }

    if(!properties.contains(CharProperty.NOTIFY)) {
        list.remove(TabItem.NOTIFY)
    }
    return list
}