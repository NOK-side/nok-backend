package com.example.nokbackend.application

import java.math.BigDecimal

data class DistanceFromLocation(
    val longitude: BigDecimal,
    val latitude: BigDecimal,
    val distance: Int
)