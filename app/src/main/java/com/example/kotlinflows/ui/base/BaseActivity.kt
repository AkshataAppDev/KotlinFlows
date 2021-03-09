package com.example.kotlinflows.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinflows.BuildConfig
import com.example.kotlinflows.R
import timber.log.Timber

open class BaseActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree());
        }
    }
}