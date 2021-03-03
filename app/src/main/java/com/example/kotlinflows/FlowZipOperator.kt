package com.example.kotlinflows

import android.os.Bundle
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking
import timber.log.Timber

/**
 * Zip combines values of two flows
 * The resulting flow completes as soon as one of the flow completes and cancel is called on remaining flows.
 */
class FlowZipOperator : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {

            val nums = (1..5).asFlow()
            val strs = flowOf("one", "two", "three")

            nums.zip(strs) { a, b -> "$a -> $b" }
                .collect { Timber.d(it) }
        }

    }

}