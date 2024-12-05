package com.sam.gogotranslation

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import gogotranslation.utils.FlipperHelper
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    private val flipperHelper by lazy {
        FlipperHelper(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            flipperHelper.init()
        }
    }

    companion object {
        lateinit var appContext: Context
    }
}
