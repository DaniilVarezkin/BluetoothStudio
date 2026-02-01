package com.daniilvarezhkin.bluetoothstudio.di

import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.BleScanner
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
}