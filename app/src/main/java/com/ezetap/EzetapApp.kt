package com.ezetap

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EzetapApp : Application() {

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: EzetapApp
            private set
    }
}