package com.example.kotlinflows.exceptions

import android.os.Bundle
import com.example.kotlinflows.ui.base.BaseActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class FlowTransparentCatchUpstreamExceptionOnly : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            simple()
                .catch { e -> Timber.e("Caught $e") } // this is not printed because catch operator honors exception transparency and catches only upstream exception i.e exception from all operators above catch
                .collect { value ->
                    check(value <= 1) { "Collected $value" }
                    Timber.d("Value $value")
                }
        }
    }

    fun simple(): Flow<Int> = flow {

        for (i in 1..3) {
            Timber.d("Emitting $i")
            emit(i)
        }

    }

}