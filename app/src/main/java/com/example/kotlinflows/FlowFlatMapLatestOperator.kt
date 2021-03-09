package com.example.kotlinflows

import android.os.Bundle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import timber.log.Timber

/**
 * FlatMapLatest is just like collectLatest.
 * It will only process the latest value. The previous flow is cancelled as soon as new flow is emitted.
 */
class FlowFlatMapLatestOperator : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            val startTime = System.currentTimeMillis()

            (1..3).asFlow().onEach {
                delay(100)

            }
                .flatMapLatest {
                    requestFlow(it)
                }
                .collect { value ->
                    Timber.d(" $value at ${System.currentTimeMillis() - startTime} ms from start")
                }
        }

    }

    fun requestFlow(i: Int): Flow<String> = flow {

        emit("$i : First")
        delay(500)
        emit("$i : Second")
    }


}