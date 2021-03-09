package com.example.kotlinflows.intermediateoperator

import android.os.Bundle
import com.example.kotlinflows.ui.base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class FlowWithMapAndTransformOperator: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {

            // map
//            (1..5).asFlow()
//                .map { request -> performRequest(request) }
//                .collect { response -> Timber.d(response)}


            // transform
            (1..3).asFlow()
                .transform { request->
                    emit("Making request $request")
                    emit(performRequest(request))
                }
                .collect { response-> Timber.d("Response : $response") }
        }
    }

    suspend fun performRequest(request: Int): String
    {
        delay(1000)
        return "Response $request"
    }
}