package com.daniilvarezhkin.bluetoothstudio.di

import android.content.Context
import com.daniilvarezhkin.bluetoothstudio.data.ble.scan.BleScannerImpl
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.BleScanner
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
}