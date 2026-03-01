package com.daniilvarezhkin.bluetoothstudio.di

import android.content.Context
import com.daniilvarezhkin.bluetoothstudio.data.ble.connection.BleConnectionFactoryImpl
import com.daniilvarezhkin.bluetoothstudio.data.ble.scan.BleScannerImpl
import com.daniilvarezhkin.bluetoothstudio.data.ble.scan.repository.BleDeviceRepositoryImpl
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.BleConnectionFactory
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.BleScanner
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.repository.BleDeviceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object DomainModule {

    @Provides
    @ViewModelScoped
    fun provideBleScanner(@ApplicationContext context: Context): BleScanner {
        return BleScannerImpl(context)
    }

    @Provides
    @ViewModelScoped
    fun provideBleDeviceRepository(bleScanner: BleScanner): BleDeviceRepository {
        return BleDeviceRepositoryImpl(bleScanner)
    }

    @Provides
    @ViewModelScoped
    fun provideBleConnectionFactory(@ApplicationContext context: Context): BleConnectionFactory {
        return BleConnectionFactoryImpl(context)
    }
}