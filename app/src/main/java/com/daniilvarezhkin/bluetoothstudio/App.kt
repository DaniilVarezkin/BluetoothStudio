package com.daniilvarezhkin.bluetoothstudio

import android.app.Application
import com.daniilvarezhkin.bluetoothstudio.data.log.AppLogTree
import com.daniilvarezhkin.bluetoothstudio.domain.log.LogRepository
import dagger.hilt.android.HiltAndroidApp
import jakarta.inject.Inject
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var logRepository: LogRepository

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Timber.plant(AppLogTree(logRepository))
    }
}