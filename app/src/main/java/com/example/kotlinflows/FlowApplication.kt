package com.example.kotlinflows

import android.app.Application

class FlowApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        System.setProperty("kotlinx.coroutines.debug", if (BuildConfig.DEBUG) "on" else "off")

    }
}