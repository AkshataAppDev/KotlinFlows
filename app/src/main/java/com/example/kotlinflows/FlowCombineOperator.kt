package com.example.kotlinflows

import android.os.Bundle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class FlowCombineOperator : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         *  numbers update every 300 ms
         *  strs update every 400 ms
         *  using zip the combined result is printed every 400 ms.
         */
//        runBlocking {
//            val nums = (1..3).asFlow().onEach { delay(300) }
//            val strs = flowOf("one", "two", "three").onEach { delay(400) }
//
//            val startTime = System.currentTimeMillis()
//            nums.zip(strs) { a, b -> "$a, $b" }
//                .collect { value ->
//                    Timber.d(" $value at ${ System.currentTimeMillis() - startTime} ms from start")
//                }
//
//        }


        /**
         * when using a combine operator a line is printed for each emission of either nums or strs.
         *
         */

        runBlocking {

            val nums = (1..3).asFlow().onEach { delay(300) } // numbers 1..3 every 300 ms
            val strs = flowOf(
                "one",
                "two",
                "three"
            ).onEach { delay(400) } // strings every 400 ms
            val startTime = System.currentTimeMillis() // remember the start time
            nums.combine(strs) { a, b -> "$a -> $b" } // compose a single string with "combine"
                .collect { value -> // collect and print
                    Timber.d("$value at ${System.currentTimeMillis() - startTime} ms from start")
                }
        }
    }
}