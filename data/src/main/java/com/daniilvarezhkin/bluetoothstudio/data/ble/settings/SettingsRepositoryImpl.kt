package com.daniilvarezhkin.bluetoothstudio.data.ble.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.daniilvarezhkin.bluetoothstudio.domain.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    private suspend fun <T> save(key: Preferences.Key<T>, value: T) {
        dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[key] = value
            }
        }
    }

    private fun <T> read(key: Preferences.Key<T>, default: T): Flow<T> = dataStore.data.map { preferences ->
        preferences[key] ?: default
    }

    override fun observeShowTimestamp(): Flow<Boolean> {
        return read(SettingsDataStoreKeys.SHOW_TIMESTAMP, true)
    }

    override suspend fun setShowTimestamp(value: Boolean) {
        save(SettingsDataStoreKeys.SHOW_TIMESTAMP, value)
    }
}