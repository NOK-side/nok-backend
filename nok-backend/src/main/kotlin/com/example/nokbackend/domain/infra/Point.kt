package com.example.nokbackend.domain.infra

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Point(
    @Column
    val value: BigDecimal,
) {
    constructor(value: Long) : this(BigDecimal.valueOf(value))

    operator fun plus(next: Point): Point = Point(value + next.value)

    operator fun minus(next: Point): Point = Point(value - next.value)

    operator fun times(next: Point): Point = Point(value * next.value)

    operator fun times(next: Int): Point = Point(value * BigDecimal.valueOf(next.toLong()))

    operator fun div(next: Point): Point = Point(value / next.value)

    operator fun compareTo(next: Point) = value.compareTo(next.value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        return this.compareTo(other) == 0
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}