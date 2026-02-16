package com.daniilvarezhkin.bluetoothstudio.presentation.screen.permission

import androidx.lifecycle.ViewModel
import com.daniilvarezhkin.bluetoothstudio.data.ble.permissions.BLUETOOTH_PERMISSIONS
import com.daniilvarezhkin.bluetoothstudio.data.ble.permissions.PermissionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class PermissionScreenViewModel @Inject constructor(
    private val permissionManager: PermissionManager
) : ViewModel() {

    fun getNotGrantedPermissions(): Array<String> {
        return permissionManager.getNotGranted(BLUETOOTH_PERMISSIONS)
    }
}