package com.daniilvarezhkin.bluetoothstudio.data.ble.scan

import android.Manifest
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import androidx.annotation.RequiresPermission
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.BleScannedDevice
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.BleScanner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BleScannerImpl(
    context: Context
) : BleScanner {
    private val _foundDevices: MutableStateFlow<List<BleScannedDevice>> = MutableStateFlow(listOf())
    private val _isScanning = MutableStateFlow(false)

    override val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    override val foundDevices: StateFlow<List<BleScannedDevice>> = _foundDevices.asStateFlow()

    private val leScanner =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager)
            ?.adapter
            ?.bluetoothLeScanner
            ?: throw RuntimeException("Bluetooth is not supported by this device")


    private val scanCallback: ScanCallback = object : ScanCallback() {
        // Вызывается при обнаружении каждого устройства.
        override fun onScanResult(callbackType: Int, result: ScanResult?) {

        }

        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onBatchScanResults(results: List<ScanResult?>?) {
            val devices = results?.let { results ->
                results.filterNotNull()
                    .map { result ->
                        BleScannedDevice(
                            name = result.device.name,
                            mac = result.device.address,
                            rssi = result.rssi
                        )
                    }
            }
        }
    }


    override fun startScan() {
        _isScanning.value = true
        //leScanner.startScan()
    }

    override fun stopScan() {
        _isScanning.value = false
    }
}