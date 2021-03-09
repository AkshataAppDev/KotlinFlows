package com.example.random

import android.os.Bundle
import com.example.kotlinflows.ui.base.BaseActivity
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class FlowsAreSequential: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Each individual collection of a flow is performed sequentially unless special operators that operate on multiple flows are used.
        // The collection works directly in the coroutine that calls a terminal operator.
        //Each emitted value is processed by all the intermediate operators from upstream to downstream and is then delivered to the terminal operator after.
        runBlocking {
            (1..5).asFlow()
                .filter {
                    Timber.d("Filter $it")
                    it % 2 == 0
                }
                .map {
                    // only if the if condition in filter is true.
                    Timber.d("Map $it")
                    "string $it"
                }
                .collect {
                    // collects string returned by map
                    Timber.d("Collect $it")
                }
        }
    }
}