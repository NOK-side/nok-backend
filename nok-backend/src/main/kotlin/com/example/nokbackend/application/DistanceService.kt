package com.example.nokbackend.application

import org.springframework.stereotype.Component
import kotlin.math.*

@Component
class DistanceService {

    fun getDistanceBetween(latitude1: Double, longitude1: Double, latitude2: Double, longitude2: Double): Int {
        val dLatitude = Math.toRadians(latitude2 - latitude1)
        val dLongitude = Math.toRadians(longitude2 - longitude1)
        val a = sin(dLatitude / 2).pow(2.0) + sin(dLongitude / 2).pow(2.0) * cos(Math.toRadians(latitude1)) * cos(Math.toRadians(latitude2))
        val c = 2 * asin(sqrt(a))
        return (R * c).toInt()
    }

    companion object {
        private const val R = 6372.8 * 1000
    }
}