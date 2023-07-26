package com.example.nokbackend.application.order

import com.example.nokbackend.domain.infra.Point
import com.example.nokbackend.domain.order.Order
import com.example.nokbackend.domain.order.OrderLine
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

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

data class OrderResponse(
    val id: Long,
    val orderMemberId: Long,
    val totalPoint: Point,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    val createDate: LocalDateTime,
    val orderLines: List<OrderLineResponse>
) {
    constructor(order: Order, orderLines: List<OrderLine>) : this(
        id = order.id,
        orderMemberId = order.orderMemberId,
        totalPoint = order.totalPoint,
        createDate = order.createDate,
        orderLines = orderLines.map { OrderLineResponse(it) }
    )
}

data class OrderLineResponse(
    val id: Long,
    val gifticonId: Long,
    val quantity: Int,
    val price: Point,
    var status: OrderLine.Status,
) {
    constructor(orderLine: OrderLine) : this(
        id = orderLine.id,
        gifticonId = orderLine.gifticonId,
        quantity = orderLine.quantity,
        price = orderLine.price,
        status = orderLine.status
    )
}