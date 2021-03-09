package com.example.kotlinflows.context

import android.os.Bundle
import com.example.kotlinflows.ui.base.BaseActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class WrongWayWithContext : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            simple().collect { value -> println(value) }
        }
    }

    fun simple(): Flow<Int> = flow {

        withContext(Dispatchers.Default) // wrong way to change context for CPU cosuming code in flow builder.
        {
            for (i in 1..3) {
                Thread.sleep(100)
                emit(i)
            }
        }

    }
}