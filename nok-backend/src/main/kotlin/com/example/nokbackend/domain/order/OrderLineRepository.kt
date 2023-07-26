package com.example.nokbackend.domain.order

import org.springframework.data.jpa.repository.JpaRepository

interface OrderLineRepository : JpaRepository<OrderLine, Long> {
    fun findAllByOrderIn(orders: List<Order>): List<OrderLine>
}