package com.example.kotlinflows.ui

import android.os.Bundle
import com.example.kotlinflows.ui.base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber


class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking<Unit> {
            launch {
                for(k in 1..3)
                {
                    Timber.d("I am not blocked")
                    delay(100)
                }
            }
            simple().collect { value-> Timber.d("Value->$value")}
        }
    }


    //flow is cold stream, need to collect.
    private fun simple() : Flow<Int> = flow {
        for(i in 1..3)
        {
            delay(100)
            emit(i)
        }
    }
}