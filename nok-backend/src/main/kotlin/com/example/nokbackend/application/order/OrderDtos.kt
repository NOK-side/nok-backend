package com.example.nokbackend.application.order

import com.example.nokbackend.domain.infra.Point

data class OrderRequest(
    val totalPrice: Point,
    val orderLines: List<OrderLineRequest>,
)

data class OrderLineRequest(
    val gifticonId: Long,
    val quantity: Int,
    val price: Point,
    val cartId: Long,
)