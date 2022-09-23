package com.example.nokbackend

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class CoroutineTest {

    @Test
    fun test01() = runBlocking {
        println("first")

        println("second")
    }

    @Test
    fun test02() = runBlocking {
        launch {
            delay(1000)
            println("second")
        }

        println("first")
    }
}