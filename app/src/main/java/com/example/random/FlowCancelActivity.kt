package com.example.random

import android.os.Bundle
import com.example.kotlinflows.ui.base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber

class FlowCancelActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            withTimeoutOrNull(250)
            {
                simple().collect { value -> Timber.d("Collect value->$value") }
            }
        }
    }


    //flow is cold stream, need to collect.
    private fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            Timber.d("Emitting Value->$i")
            emit(i)
        }
    }
}