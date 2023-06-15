package com.example.nokbackend.fixture

import com.example.nokbackend.application.order.OrderLineRequest
import com.example.nokbackend.application.order.OrderRequest
import com.example.nokbackend.domain.order.OrderLine
import com.example.nokbackend.domain.order.Orders
import java.math.BigDecimal

fun aOrderRequest(
    totalPrice: BigDecimal = aGifticon().price,
    orderLineRequests: List<OrderLineRequest> = listOf(aOrderLineRequest()),
): OrderRequest = OrderRequest(
    totalPrice = totalPrice,
    orderLines = orderLineRequests
)

fun aOrderLineRequest(
    gifticonId: Long = aGifticon().id,
    quantity: Int = 1,
    price: BigDecimal = aGifticon().price,
): OrderLineRequest = OrderLineRequest(
    gifticonId = gifticonId,
    quantity = quantity,
    price = price
)

fun aOrder(
    orderMemberId: Long = aMember().id,
    totalPrice: BigDecimal = aOrderRequest().totalPrice
): Orders = Orders(
    orderMemberId = orderMemberId,
    totalPoint = totalPrice
)

fun aOrderLine(
    orders: Orders = aOrder(),
    gifticonId: Long = aGifticon().id,
    quantity: Int = aOrderLineRequest().quantity,
    price: BigDecimal = aOrderLineRequest().price,
    status: OrderLine.Status = OrderLine.Status.ORDER
): OrderLine = OrderLine(
    orders = orders,
    gifticonId = gifticonId,
    quantity = quantity,
    price = price,
    status = status,
)