package com.daniilvarezhkin.bluetoothstudio.data.ble.scan

import android.Manifest
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.BleScanner
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.model.BleScanResult
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.model.BleScannedDevice
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogRepository
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogTag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import java.util.concurrent.ConcurrentHashMap

class BleScannerImpl(
    val context: Context,
    val logRepository: LogRepository,
) : BleScanner {
    private val foundDeviceMap = ConcurrentHashMap<String, BleScannedDevice>()
    private val _foundDevices: MutableStateFlow<BleScanResult> = MutableStateFlow(BleScanResult())

    override val foundDevices: StateFlow<BleScanResult> = _foundDevices.asStateFlow()
    private val _isScanning = MutableStateFlow(false)

    override val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val defaultScanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
        .build()

    private val defaultScanFilters = emptyList<ScanFilter>()

    private val leScanner: BluetoothLeScanner =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager)
            ?.adapter
            ?.bluetoothLeScanner
            ?: throw RuntimeException("Bluetooth is not supported by this device")


    private val scanCallback: ScanCallback = object : ScanCallback() {
        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            Timber.v("Обновлен результат сканирования")
            if (result == null) return

            val scannedDevice = BleScannedDevice(
                name = result.device.name ?: result.scanRecord?.deviceName,
                address = result.device.address,
                rssi = result.rssi
            )

            foundDeviceMap[scannedDevice.address] = scannedDevice

            _foundDevices.value = BleScanResult(foundDeviceMap.values.sortedByDescending { device -> device.rssi })
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            logRepository.addBLog(Log.ERROR, LogTag.APP, "ScanFailed. Error code: $errorCode")

        }
    }

    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT])
    override fun startScan() {
        if(!isScanning.value) {
            logRepository.addBLog(Log.INFO, LogTag.APP, "Start scanning")
            leScanner.startScan(scanCallback)
            _isScanning.value = true
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    override fun stopScan() {
        if(isScanning.value) {
            logRepository.addBLog(Log.INFO, LogTag.APP, "Stop scanning")
            leScanner.stopScan(scanCallback)
            _isScanning.value = false
        }
    }
}