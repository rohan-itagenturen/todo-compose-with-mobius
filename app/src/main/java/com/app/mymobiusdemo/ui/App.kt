package com.app.mymobiusdemo.ui

import android.app.Application
import com.spotify.mobius.android.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    with(element) { return "($fileName:$lineNumber)$methodName()" }
                }
            })
        }
        RxJavaPlugins.setErrorHandler {
            Timber.e(it)
        }
    }
}