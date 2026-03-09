package com.daniilvarezhkin.bluetoothstudio.di

import android.content.Context
import com.daniilvarezhkin.bluetoothstudio.data.ble.connection.BleConnectionFactoryImpl
import com.daniilvarezhkin.bluetoothstudio.data.ble.connection.GattMapper
import com.daniilvarezhkin.bluetoothstudio.data.ble.scan.BleScannerImpl
import com.daniilvarezhkin.bluetoothstudio.data.ble.scan.repository.BleDeviceRepositoryImpl
import com.daniilvarezhkin.bluetoothstudio.data.ble.specification.loader.AssignedCharacteristicListLoaderImpl
import com.daniilvarezhkin.bluetoothstudio.data.ble.specification.loader.AssignedServiceListLoaderImpl
import com.daniilvarezhkin.bluetoothstudio.data.ble.specification.repository.AssignedCharacteristicRepositoryImpl
import com.daniilvarezhkin.bluetoothstudio.data.ble.specification.repository.AssignedServiceRepositoryImpl
import com.daniilvarezhkin.bluetoothstudio.domain.ble.connection.BleConnectionFactory
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.BleScanner
import com.daniilvarezhkin.bluetoothstudio.domain.ble.scan.repository.BleDeviceRepository
import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.loader.AssignedCharacteristicListLoader
import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.loader.AssignedServiceListLoader
import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.repository.AssignedCharacteristicRepository
import com.daniilvarezhkin.bluetoothstudio.domain.ble.specification.repository.AssignedServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.coroutines.runBlocking
import timber.log.Timber

@Module
@InstallIn(ViewModelComponent::class)
internal object ScanModule {

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
}

@Module
@InstallIn(ViewModelComponent::class)
internal object ConnectModule {

    @Provides
    @ViewModelScoped
    fun provideBleConnectionFactory(@ApplicationContext context: Context, gattMapper: GattMapper): BleConnectionFactory {
        return BleConnectionFactoryImpl(context, gattMapper)
    }


}

@Module
@InstallIn(SingletonComponent::class)
internal object MapperModule {
    @Provides
    @Singleton
    fun provideGattMapper(
        assignedServiceRepository: AssignedServiceRepository,
        assignedCharacteristicRepository: AssignedCharacteristicRepository
    ): GattMapper {
        return GattMapper(assignedServiceRepository, assignedCharacteristicRepository)
    }
}


@Module
@InstallIn(SingletonComponent::class)
internal object SpecificationModule {

    @Provides
    @Singleton
    fun provideAssignedServiceListLoader(@ApplicationContext context: Context): AssignedServiceListLoader {
        return AssignedServiceListLoaderImpl(context)
    }

    @Provides
    @Singleton
    fun provideAssignedCharacteristicListLoader(@ApplicationContext context: Context): AssignedCharacteristicListLoader {
        return AssignedCharacteristicListLoaderImpl(context)
    }

    @Provides
    @Singleton
    fun provideAssignedServiceRepository(loader: AssignedServiceListLoader): AssignedServiceRepository {
        return try {
            runBlocking {
                Timber.i("Загрузка стандартных сервисов")
                AssignedServiceRepositoryImpl(loader.load().uuids)
            }
        } catch (e: Exception) {
            Timber.e("Ошибка загрузки назначенных сервисов")
            AssignedServiceRepositoryImpl(emptyList())
        }
    }

    @Provides
    @Singleton
    fun provideAssignedCharacteristicRepository(loader: AssignedCharacteristicListLoader): AssignedCharacteristicRepository {
        return try {
            runBlocking {
                Timber.i("Загрузка стандартных характеристик")
                AssignedCharacteristicRepositoryImpl(loader.load().uuids)
            }
        } catch (e: Exception) {
            Timber.e("Ошибка загрузки назначенных характеристик")
            AssignedCharacteristicRepositoryImpl(emptyList())
        }
    }
}