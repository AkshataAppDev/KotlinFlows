package com.example.kotlinflows

import android.os.Bundle
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class TerminalFlowOperators : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {

            val sum = (1..5).asFlow()
                .map { it * it }
                .reduce { a, b -> a + b }

            Timber.d("Sum: $sum")
        }

    }
}