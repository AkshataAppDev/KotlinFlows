package com.example.kotlinflows.exceptions

import android.os.Bundle
import com.example.kotlinflows.ui.base.BaseActivity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class FlowCatchingExceptionDeclaratively : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            simple()
                .onEach { value -> // oneach to handle all exceptions from upstream or downstream
                    check(value <= 1) { "Collected $value" }
                    Timber.d("Value->$value")
                }
                .catch { e -> Timber.e("Caught using onEach $e") }
                .collect() // in such a case collect must be without any parameters
        }
    }

    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            Timber.d("Emitting $i")
            emit(i)
        }
    }
}