package com.example.nokbackend

import com.example.nokbackend.application.geometry.GeometryService
import com.example.nokbackend.application.geometry.Point
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

    @Test
    fun test05() {
        val longitude1 = 129.4105747991
        val longitude2 = 129.3755938472
        val latitude1 = 36.6003009541
        val latitude2 = 36.2824187049

        val (latitude3, longitude3) = GeometryService().getCenterOfSpots(listOf(Point(longitude1, latitude1), Point(longitude2, latitude2)))

        println("$longitude3 $latitude3")

    }
}