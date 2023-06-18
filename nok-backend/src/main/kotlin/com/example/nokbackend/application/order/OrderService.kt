package com.example.nokbackend.application.order

import com.example.nokbackend.application.gifticon.BuyGifticonRequest
import com.example.nokbackend.application.gifticon.MemberGifticonService
import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.memberpoint.MemberPointRepository
import com.example.nokbackend.domain.memberpoint.findByMemberIdCheck
import com.example.nokbackend.domain.order.OrderLine
import com.example.nokbackend.domain.order.OrderLineRepository
import com.example.nokbackend.domain.order.OrderRepository
import com.example.nokbackend.domain.order.Orders
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderLineRepository: OrderLineRepository,
    private val gifticonRepository: GifticonRepository,
    private val memberPointRepository: MemberPointRepository,
    private val memberGifticonService: MemberGifticonService,
) {

    fun registerOrder(member: Member, orderRequest: OrderRequest) {
        validate(member, orderRequest)

        val orders = Orders(member.id, orderRequest.totalPrice)
        orderRepository.save(orders)

        orderRequest.orderLines.forEach {
            val orderLine = OrderLine(orders, it.gifticonId, it.quantity, it.price, OrderLine.Status.ORDER)
            orderLineRepository.save(orderLine)
        }

        orderRequest.orderLines.forEach {
            memberGifticonService.registerMemberGifticon(member, BuyGifticonRequest(it.gifticonId, it.quantity))
        }
    }

    fun validate(member: Member, orderRequest: OrderRequest) {
        val orderLines = orderRequest.orderLines

        val totalPrice = orderLines.map { it.price * it.quantity }
            .reduce { x, y -> x + y }
        check(orderRequest.totalPrice == totalPrice) { "요청 금액이 일치하지 않습니다" }

        val memberPoint = memberPointRepository.findByMemberIdCheck(member.id)
        check(memberPoint.point >= totalPrice) { "보유 포인트가 부족합니다" }

        val gifticons = gifticonRepository.findAllById(orderLines.map { it.gifticonId })
        orderLines.forEach {
            check(it.price == gifticons.getById(it.gifticonId).price) { "상품 금액이 일치하지 않습니다" }
        }

        memberPoint.point -= totalPrice
    }

    private fun List<Gifticon>.getById(id: Long): Gifticon {
        return find { it.id == id } ?: throw RuntimeException("기프티콘이 존재하지 않습니다.")
    }
}