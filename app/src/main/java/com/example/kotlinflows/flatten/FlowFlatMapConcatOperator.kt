package com.example.kotlinflows.flatten

import android.os.Bundle
import com.example.kotlinflows.ui.base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class FlowFlatMapConcatOperator: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {

            val startTime = System.currentTimeMillis()
            (1..3).asFlow().onEach {
                delay(100)
            }
                .flatMapConcat {
                    requestFlow(it)
                }
                .collect { value ->
                    Timber.d("$value at ${System.currentTimeMillis()- startTime} ms from start")
                }
        }

    }

    fun requestFlow(i: Int) : Flow<String> = flow {
        emit("$i: First")
        delay(500)
        emit("$i: Second")
    }
}

/**
        Output:


        NOTE: There is 100 ms gap between 2:, 3: and the inner flow ie. requestflow is completed for each element.


        2021-03-08 12:04:51.218 20713-20713/com.example.kotlinflows D/FlowFlatMapConcatOperator$onCreate$1$invokeSuspend$$inlined$collect: 1: First at 158 ms from start
        2021-03-08 12:04:51.723 20713-20713/com.example.kotlinflows D/FlowFlatMapConcatOperator$onCreate$1$invokeSuspend$$inlined$collect: 1: Second at 661 ms from start
        2021-03-08 12:04:51.829 20713-20713/com.example.kotlinflows D/FlowFlatMapConcatOperator$onCreate$1$invokeSuspend$$inlined$collect: 2: First at 768 ms from start
        2021-03-08 12:04:52.335 20713-20713/com.example.kotlinflows D/FlowFlatMapConcatOperator$onCreate$1$invokeSuspend$$inlined$collect: 2: Second at 1273 ms from start
        2021-03-08 12:04:52.442 20713-20713/com.example.kotlinflows D/FlowFlatMapConcatOperator$onCreate$1$invokeSuspend$$inlined$collect: 3: First at 1380 ms from start
        2021-03-08 12:04:52.947 20713-20713/com.example.kotlinflows D/FlowFlatMapConcatOperator$onCreate$1$invokeSuspend$$inlined$collect: 3: Second at 1886 ms from start



 **/