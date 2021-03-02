package com.example.kotlinflows

import android.os.Bundle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import kotlin.system.measureTimeMillis


//Before using buffer()

//    delay 100ms                        delay 100ms                        delay 100ms
//               |emit                   |          |emit                   |           |emit
//               |delay for 300ms -> print          |delay for 300ms ->print            |delay for 300ms -> print


//After using buffer
//    delay 100ms|delay100ms | delay100ms |
//               |delay 300ms -> print  |  delay 300ms ->  print | delay 300ms -> print



class FlowBuffering : BaseActivity() {

    fun log(msg: String) = Timber.d("[${Thread.currentThread().name}] $msg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            val time = measureTimeMillis {
                simple()
                    .buffer() // buffering to speed up
                    .collect { value ->
                    delay(300)
                    log("Collected:$value")
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