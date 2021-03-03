package com.example.kotlinflows

import android.os.Bundle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import kotlin.system.measureTimeMillis

/**
 * Conflation is one way to speed up processing when both the emitter and collector are slow.
 * It does it by dropping emitted values.
 * The other way is to cancel a slow collector and restart it every time a new value is emitted.
 * There is a family of xxxLatest operators that perform the same essential logic of a xxx operator, but cancel the code in their block on a new value.
 */
class FlowCollectLatestOperator : BaseActivity() {

    fun log(msg: String) = Timber.d("[${Thread.currentThread().name}] $msg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            val time = measureTimeMillis {
                simple()
                    .collectLatest { value -> // cancel and restart on the latest value
                        log("Collecting $value")
                        delay(300)
                        log("Done $value")

                    }
            }
            log("Collected in $time ms")
        }
    }

    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            log("Emitting $i")
            emit(i)
        }
    }
}