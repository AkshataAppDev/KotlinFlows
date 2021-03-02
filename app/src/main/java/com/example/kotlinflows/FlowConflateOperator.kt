package com.example.kotlinflows

import android.os.Bundle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import kotlin.system.measureTimeMillis

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