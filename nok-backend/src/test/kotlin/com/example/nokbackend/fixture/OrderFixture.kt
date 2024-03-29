package com.example.nokbackend.fixture

import com.example.nokbackend.application.order.OrderLineRequest
import com.example.nokbackend.application.order.OrderRequest
import com.example.nokbackend.domain.infra.Point
import com.example.nokbackend.domain.order.OrderLine
import com.example.nokbackend.domain.order.Order

fun aOrderRequest(
    totalPrice: Point = aGifticon().price,
    orderLineRequests: List<OrderLineRequest> = listOf(aOrderLineRequest()),
): OrderRequest = OrderRequest(
    totalPrice = totalPrice,
    orderLines = orderLineRequests
)

fun aOrderLineRequest(
    gifticonId: Long = aGifticon().id,
    quantity: Int = 1,
    price: Point = aGifticon().price,
    cartId: Long = 0L
): OrderLineRequest = OrderLineRequest(
    gifticonId = gifticonId,
    quantity = quantity,
    price = price,
    cartId = cartId
)

fun aOrder(
    orderMemberId: Long = aMember().id,
    totalPrice: Point = aOrderRequest().totalPrice
): Order = Order(
    orderMemberId = orderMemberId,
    totalPoint = totalPrice
)

fun aOrderLine(
    order: Order = aOrder(),
    gifticonId: Long = aGifticon().id,
    quantity: Int = aOrderLineRequest().quantity,
    price: Point = aOrderLineRequest().price,
    status: OrderLine.Status = OrderLine.Status.ORDER
): OrderLine = OrderLine(
    order = order,
    gifticonId = gifticonId,
    quantity = quantity,
    price = price,
    status = status,
)