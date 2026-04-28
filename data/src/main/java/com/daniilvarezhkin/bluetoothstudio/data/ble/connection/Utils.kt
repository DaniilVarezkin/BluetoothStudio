package com.daniilvarezhkin.bluetoothstudio.data.ble.connection

fun Int.toGattStatusString(): String = when (this) {
    0 -> "GATT_SUCCESS"
    8 -> "GATT_CONN_TIMEOUT"
    19 -> "GATT_CONN_TERMINATE_PEER_USER"
    22 -> "GATT_CONN_TERMINATE_LOCAL_HOST"
    62 -> "GATT_CONN_FAIL_ESTABLISH"
    133 -> "GATT_ERROR (General)"
    257 -> "GATT_FAILURE"
    else -> "UNKNOWN_ERROR"
}