package com.example.nokbackend.domain.authentication

import com.example.nokbackend.domain.infra.Point
import org.junit.jupiter.api.Test

class InfraTest {

    @Test
    fun pointTest() {
        var a = Point(1)
        val b = Point(2)
        val c = Point(2)

        println(a > b)
        println(a < b)
        println(b == c)
        println(b === c)

        a += c
        println(a)
    }
}