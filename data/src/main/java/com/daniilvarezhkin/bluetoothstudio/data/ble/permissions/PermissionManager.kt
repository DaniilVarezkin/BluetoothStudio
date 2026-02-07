package com.daniilvarezhkin.bluetoothstudio.data.ble.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionManager(
    private val context: Context
) {
    fun check(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun check(permissions: Array<String>): Boolean {
        return permissions.all { permission -> check(permission) }
    }

    fun getNotGranted(permissions: Array<String>): Array<String> {
        return permissions.filterNot { permission ->
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            }.toTypedArray()
    }
}