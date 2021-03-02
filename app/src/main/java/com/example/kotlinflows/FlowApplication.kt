package com.example.kotlinflows

import android.app.Application

class FlowApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // Required to printout coroutines names like [main @coroutine#2], [main @coroutine#1]
        System.setProperty("kotlinx.coroutines.debug", if (BuildConfig.DEBUG) "on" else "off")

    }
}