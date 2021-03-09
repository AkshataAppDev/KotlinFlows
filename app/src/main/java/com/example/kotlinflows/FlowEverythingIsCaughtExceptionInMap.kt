package com.example.kotlinflows

import android.os.Bundle
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class FlowEverythingIsCaughtExceptionInMap : BaseActivity() {

    fun simple(): Flow<String> =
        flow {
            for (i in 1..3) {
                Timber.d("Emitting $i")
                emit(i)
            }

        }.map { value ->
            check(value <= 1) { "Crashed at $value" } // returned when check is false
            "string $value" // returned when check is true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {
            try {
                simple().collect { value ->

                    Timber.d("Return Value $value")
                }

            } catch (e: Throwable) {
                Timber.e("Caught $e")
            }
        }
    }
}

    /**

    Emitting 1
    string 1
    Emitting 2
    Caught java.lang.IllegalStateException: Crashed on 2

     **/