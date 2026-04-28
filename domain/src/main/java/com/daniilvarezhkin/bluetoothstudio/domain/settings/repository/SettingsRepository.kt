package com.daniilvarezhkin.bluetoothstudio.domain.settings.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observeShowTimestamp(): Flow<Boolean>
    suspend fun setShowTimestamp(value: Boolean)
}