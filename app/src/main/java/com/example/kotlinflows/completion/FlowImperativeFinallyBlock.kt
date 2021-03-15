package com.example.kotlinflows.completion

import android.os.Bundle
import com.example.kotlinflows.ui.base.BaseActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class FlowImperativeFinallyBlock : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            try {
                simple().collect { value ->
                    Timber.d("Value : $value")
                }
            } finally {  // use finally to execute an action on completion
                Timber.d("Inside finally")
            }
        }

    }

    fun simple(): Flow<Int> = (1..3).asFlow()

}


/**
1
2
3
Done
 **/