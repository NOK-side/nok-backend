package com.example.nokbackend.domain.infra

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Location(
    @Column
    val roadNameAddress: String,

    @Column
    val landNumberAddress: String,

    @Column(precision = 21, scale = 10)
    val latitude: BigDecimal,

    @Column(precision = 21, scale = 10)
    val longitude: BigDecimal,
)