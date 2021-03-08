package com.example.kotlinflows

import android.os.Bundle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import timber.log.Timber


/**
 * we end up with a flow of flows (Flow<Flow<String>>) that needs to be flattened into a single flow for further processing.
 */
class FlowOfFlows : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {

            (1..3).asFlow().map { requestFlow(it) }
                .collect {
                    it.collect {
                        Timber.d("This is a flow $it")
                    }
                }
        }
    }

    /**
     * method that returns a flow of string for each given element.
     */
    fun requestFlow(i: Int): Flow<String> = flow {
        emit("$i: First")
        delay(500)
        emit("$i: Second")
    }
}