package com.ilnur.modimporter

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DEBUG_PROPERTY_NAME
import kotlinx.coroutines.DEBUG_PROPERTY_VALUE_ON

@HiltAndroidApp
class ModImporter: Application() {

    override fun onCreate() {
        super.onCreate()
            //System.setProperty(DEBUG_PROPERTY_NAME, DEBUG_PROPERTY_VALUE_ON)
    }
}