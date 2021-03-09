package com.example.kotlinflows.buffering

import android.os.Bundle
import com.example.kotlinflows.ui.base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import kotlin.system.measureTimeMillis

/**
 * Conflation is used when receiver and producer both are slow.
 */
class FlowConflateOperator : BaseActivity() {

    fun log(msg: String) = Timber.d("[${Thread.currentThread().name}] $msg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            val time = measureTimeMillis {
                simple()
                    .conflate() // conflate, dont process each emission
                    .collect { value ->
                        delay(300)
                        log("Value : $value")
                    }
            }
            println("Collected in $time ms")
        }

    }

    fun simple(): Flow<Int> = flow {

        for (i in 1..12) {
            delay(100)
            log("Emitting $i")
            emit(i)
        }

    }
}