package com.example.kotlinflows.sizelimitoperator

import android.os.Bundle
import com.example.kotlinflows.ui.base.BaseActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class TakeFlowOperator : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            numbers().take(2)
                .collect { value -> Timber.d("Value->$value") }
        }
    }

    fun numbers(): Flow<Int> = flow {
        try { // take throws an exception when cancelled.
            emit(1)
            emit(2)
            Timber.d("This line will not execute")
            emit(3)
        } finally {
            Timber.d("Finally in numbers")
        }
    }
}

