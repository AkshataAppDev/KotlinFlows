package com.example.kotlinflows.exceptions

import android.os.Bundle
import com.example.kotlinflows.ui.base.BaseActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class FlowCollectorTryAndCatch : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {

            try {

                simple().collect { value ->
                    Timber.d("Printing $value")
                    // kotlin's check method.
                    check(value <= 1) { "Collected $value" }
                }

            } catch (e: Throwable) {
                Timber.e("Caught $e")
            }
        }
    }

    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            Timber.d("Emitting $i")
            emit(i)
        }
    }

}