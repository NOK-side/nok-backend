package com.example.nokbackend.application.order

import com.example.nokbackend.application.cart.CartCommandService
import com.example.nokbackend.application.gifticon.BuyGifticonRequest
import com.example.nokbackend.application.gifticon.MemberGifticonCommandService
import com.example.nokbackend.application.point.PointCommandService
import com.example.nokbackend.application.point.SpendPointRequest
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.infra.Point
import com.example.nokbackend.domain.member.Member
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
    private val memberGifticonCommandService: MemberGifticonCommandService,
    private val cartCommandService: CartCommandService,
    private val pointCommandService: PointCommandService
) {

    fun registerOrder(member: Member, orderRequest: OrderRequest) {
        val totalPrice = calculateTotalPrice(orderRequest.orderLines)
        check(orderRequest.totalPrice == totalPrice) { "요청 금액이 일치하지 않습니다" }

        val gifticonMap = gifticonRepository.findAllById(orderRequest.orderLines.map { it.gifticonId })
            .associateBy { it.id }

        orderRequest.orderLines.forEach {
            val gifticon = gifticonMap[it.gifticonId] ?: throw RuntimeException("기프티콘이 존재하지 않습니다.")
            check(it.price == gifticon.price) { "상품 금액이 일치하지 않습니다" }
        }

        val order = Order(member.id, orderRequest.totalPrice)
        orderRepository.save(order)

        val orderLines = orderRequest.orderLines
            .map {
            memberGifticonCommandService.registerMemberGifticon(member, BuyGifticonRequest(it.gifticonId, it.quantity))

            if (it.cartId != 0L) {
                cartCommandService.deleteItemFromCart(member, it.cartId)
            }

            OrderLine(order, it.gifticonId, it.quantity, it.price, OrderLine.Status.ORDER)
        }

        orderLineRepository.saveAll(orderLines)
        pointCommandService.spendPoint(member, SpendPointRequest(totalPrice))
    }

    private fun calculateTotalPrice(orderLines: List<OrderLineRequest>): Point =
        orderLines.map { it.price * it.quantity }.reduce { x, y -> x + y }
}