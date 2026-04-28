package com.daniilvarezhkin.bluetoothstudio.data.ble.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

const val SETTINGS_DATA_STORE = "settings_data_store"
val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_DATA_STORE)

object SettingsDataStoreKeys {
    val SHOW_TIMESTAMP = booleanPreferencesKey("show_timestamp")
}