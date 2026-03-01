package com.daniilvarezhkin.bluetoothstudio.di

import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.BleConnectionFactory
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.usecase.GetBleConnectionUseCase
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.BleScanner
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.repository.BleDeviceRepository
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase.GetFoundedDevicesUseCase
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase.GetScanStateUseCase
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase.StartScanBleUseCase
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.usecase.StopScanBleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideStartScanBleUseCase(bleScanner: BleScanner): StartScanBleUseCase {
        return StartScanBleUseCase(bleScanner)
    }

    @Provides
    @ViewModelScoped
    fun provideStopScanBleUseCase(bleScanner: BleScanner): StopScanBleUseCase {
        return StopScanBleUseCase(bleScanner)
    }

    @Provides
    @ViewModelScoped
    fun provideGetScanStateUseCase(bleScanner: BleScanner): GetScanStateUseCase {
        return GetScanStateUseCase(bleScanner)
    }

    @Provides
    @ViewModelScoped
    fun provideGetFoundedDevicesUseCase(bleDeviceRepository: BleDeviceRepository): GetFoundedDevicesUseCase {
        return GetFoundedDevicesUseCase(bleDeviceRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetBleConnectionUseCase(factory: BleConnectionFactory): GetBleConnectionUseCase {
        return GetBleConnectionUseCase(factory)
    }
}