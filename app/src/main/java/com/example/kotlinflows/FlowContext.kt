package com.example.kotlinflows

import android.os.Bundle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import timber.log.Timber


/**
 * context preservation  : if there is a simple flow, then the following code runs in the context specified by the author of this code, regardless of the implementation details of the simple flow:
 */
class FlowContext : BaseActivity() {

    fun log(msg: String) = Timber.d("[${Thread.currentThread().name}] $msg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            simple().collect { value -> log("Collected $value") }
        }

    }

    fun simple(): Flow<Int> = flow {
        log("Started simple flow")
        for (i in 1..3) {
            emit(i)
        }
    }


}