package com.example.nokbackend.application.order

import com.example.nokbackend.application.cart.CartCommandService
import com.example.nokbackend.application.gifticon.BuyGifticonRequest
import com.example.nokbackend.application.gifticon.MemberGifticonCommandService
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.memberpoint.MemberPointRepository
import com.example.nokbackend.domain.memberpoint.findByMemberIdCheck
import com.example.nokbackend.domain.order.Order
import com.example.nokbackend.domain.order.OrderLine
import com.example.nokbackend.domain.order.OrderLineRepository
import com.example.nokbackend.domain.order.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OrderCommandService(
    private val orderRepository: OrderRepository,
    private val orderLineRepository: OrderLineRepository,
    private val gifticonRepository: GifticonRepository,
    private val memberPointRepository: MemberPointRepository,
    private val memberGifticonCommandService: MemberGifticonCommandService,
    private val cartCommandService: CartCommandService,
) {

    fun registerOrder(member: Member, orderRequest: OrderRequest) {
        validate(member, orderRequest)

        val order = Order(member.id, orderRequest.totalPrice)
        orderRepository.save(order)

        orderRequest.orderLines.forEach {
            val orderLine = OrderLine(order, it.gifticonId, it.quantity, it.price, OrderLine.Status.ORDER)
            orderLineRepository.save(orderLine)
        }

        orderRequest.orderLines.forEach {
            memberGifticonCommandService.registerMemberGifticon(member, BuyGifticonRequest(it.gifticonId, it.quantity))
            if (it.cartId != 0L) {
                cartCommandService.deleteItemFromCart(member, it.cartId)
            }
        }
    }

    fun validate(member: Member, orderRequest: OrderRequest) {
        val orderLines = orderRequest.orderLines

        val totalPrice = orderLines.map { it.price * it.quantity }
            .reduce { x, y -> x + y }
        check(orderRequest.totalPrice == totalPrice) { "요청 금액이 일치하지 않습니다" }

        val memberPoint = memberPointRepository.findByMemberIdCheck(member.id)
        check(memberPoint.point >= totalPrice) { "보유 포인트가 부족합니다" }

        val gifticonMap = gifticonRepository.findAllById(orderLines.map { it.gifticonId })
            .associateBy { it.id }

        orderLines.forEach {
            val gifticon = gifticonMap[it.gifticonId] ?: throw RuntimeException("기프티콘이 존재하지 않습니다.")
            check(it.price == gifticon.price) { "상품 금액이 일치하지 않습니다" }
        }

        memberPoint.point -= totalPrice
    }
}