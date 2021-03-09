package com.example.kotlinflows

import android.os.Bundle
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import timber.log.Timber


/**
 *
 * Flows must be transparent to exceptions and it is a violation of the exception transparency to emit values in the flow { ... } builder from inside of a try/catch block.
 *
 * This guarantees that a collector throwing an exception can always catch it using try/catch as in the previous example.

The emitter can use a catch operator that preserves this exception transparency and allows encapsulation of its exception handling. The body of the catch operator can analyze an exception and react to it in different ways depending on which exception was caught:

Exceptions can be rethrown using throw.

Exceptions can be turned into emission of values using emit from the body of catch.

Exceptions can be ignored, logged, or processed by some other code.


 *
 */
class FlowExceptionEncapsulationWithoutTryCatch : BaseActivity() {

    fun simple(): Flow<String> =
        flow {

            for (i in 1..3) {
                Timber.d("Emitting $i")
                emit(i)
            }
        }
            .map { value ->

                check(value <= 1) { "Crashed on $value" } //3
                "String $value"
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking {

            simple()
                .catch { e -> emit("Caught $e") } // encapsulating catch &  emitting text on exception from catch body //2
                .collect { value -> Timber.d("Value returned : $value") } // 1
        }
    }
}

/**
2021-03-09 16:30:27.479 29030-29030/com.example.kotlinflows D/FlowExceptionEncapsulationWithoutTryCatch$simple: Emitting 1
2021-03-09 16:30:27.511 29030-29030/com.example.kotlinflows D/FlowExceptionEncapsulationWithoutTryCatch$onCreate$1$invokeSuspend$$inlined$collect: Value returned : String 1
2021-03-09 16:30:27.512 29030-29030/com.example.kotlinflows D/FlowExceptionEncapsulationWithoutTryCatch$simple: Emitting 2
2021-03-09 16:30:27.518 29030-29030/com.example.kotlinflows D/FlowExceptionEncapsulationWithoutTryCatch$onCreate$1$invokeSuspend$$inlined$collect: Value returned : Caught java.lang.IllegalStateException: Crashed on 2

        **/