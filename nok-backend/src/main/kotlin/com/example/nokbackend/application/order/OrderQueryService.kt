package com.example.nokbackend.application.order

import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.order.OrderLineRepository
import com.example.nokbackend.domain.order.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class OrderQueryService(
    private val orderRepository: OrderRepository,
    private val orderLineRepository: OrderLineRepository,
    private val gifticonRepository: GifticonRepository,
) {

    fun findMyOrder(member: Member): List<OrderResponse> {
        val orders = orderRepository.findByOrderMemberId(member.id)
        val orderLines = orderLineRepository.findAllByOrderIn(orders)
        val orderLinesMap = orderLines.groupBy { it.order.id }

        val gifticonMap = gifticonRepository.findAllById(orderLines.map { it.gifticonId })
            .associateBy { it.id }

        return orders.map {
            OrderResponse(it, orderLinesMap[it.id] ?: emptyList(), gifticonMap)
        }
    }
}