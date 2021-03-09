package com.example.kotlinflows.flatten

import android.os.Bundle
import com.example.kotlinflows.ui.base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class FlowFlatMapMergeOperator : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {

            val startTime = System.currentTimeMillis()
            (1..5).asFlow()
                .onEach { delay(100) }
                .flatMapMerge {
                    requestFlow(it)
                }
                .collect {

                    Timber.d("$it at ${System.currentTimeMillis() - startTime}")
                }
        }
    }

    fun requestFlow(i: Int): Flow<String> = flow {

        emit("$i: First")
        delay(500)
        emit("$i: Second")
    }
}

/**
Output;

NOTE : Concurrently collect all incoming flows and merge their values to single flow so values are emitted asap.

2021-03-08 12:25:53.158 20989-20989/com.example.kotlinflows D/FlowFlatMapMergeOperator$onCreate$1$invokeSuspend$$inlined$collect: 1: First at 177
2021-03-08 12:25:53.251 20989-20989/com.example.kotlinflows D/FlowFlatMapMergeOperator$onCreate$1$invokeSuspend$$inlined$collect: 2: First at 270
2021-03-08 12:25:53.358 20989-20989/com.example.kotlinflows D/FlowFlatMapMergeOperator$onCreate$1$invokeSuspend$$inlined$collect: 3: First at 373
2021-03-08 12:25:53.660 20989-20989/com.example.kotlinflows D/FlowFlatMapMergeOperator$onCreate$1$invokeSuspend$$inlined$collect: 1: Second at 678
2021-03-08 12:25:53.753 20989-20989/com.example.kotlinflows D/FlowFlatMapMergeOperator$onCreate$1$invokeSuspend$$inlined$collect: 2: Second at 772
2021-03-08 12:25:53.859 20989-20989/com.example.kotlinflows D/FlowFlatMapMergeOperator$onCreate$1$invokeSuspend$$inlined$collect: 3: Second at 878



 **/