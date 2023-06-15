package com.example.nokbackend.application.order

import java.math.BigDecimal

data class OrderRequest(
    val totalPrice: BigDecimal,
    val orderLines: List<OrderLineRequest>,
)

data class OrderLineRequest(
    val gifticonId: Long,
    val quantity: Int,
    val price: BigDecimal,
)