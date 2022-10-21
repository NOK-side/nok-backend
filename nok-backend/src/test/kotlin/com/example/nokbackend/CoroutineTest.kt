package com.example.nokbackend

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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

    @Test
    fun test04() {
        val pattern = DateTimeFormatter.ofPattern("yyyyMMdd")
        val take = LocalDate.now().format(pattern) + UUID.randomUUID().toString().take(8).uppercase()
        println(take)
    }
}