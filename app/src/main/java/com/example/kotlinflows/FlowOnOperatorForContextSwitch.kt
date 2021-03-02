package com.example.kotlinflows

import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import timber.log.Timber


/**
 * Another thing to observe here is that the flowOn operator has changed the default sequential nature of the flow. Now collection happens in one coroutine ("coroutine#1") and emission happens in another coroutine ("coroutine#2") that is running in another thread concurrently with the collecting coroutine. The flowOn operator creates another coroutine for an upstream flow when it has to change the CoroutineDispatcher in its context.


 */
class FlowOnOperatorForContextSwitch: BaseActivity() {


    fun log(msg: String) = Timber.d("[${Thread.currentThread().name}] $msg")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        runBlocking {
            simple().collect {
                value->
                log("Collected $value->")
            }
        }
    }

    fun simple() : Flow<Int> = flow {

        for(i in  1..3) {
            Thread.sleep(100) // pretend to be computing a CPU consuming task
            log("Emitting $i")
            emit(i)
        }
    }.flowOn(Dispatchers.Default) // RIGHT WAY to change context for CPU consuming task in flow builder


}