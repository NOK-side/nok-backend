package com.example.nokbackend.application

import com.example.nokbackend.application.cart.CartService
import com.example.nokbackend.application.gifticon.MemberGifticonService
import com.example.nokbackend.application.order.OrderRequest
import com.example.nokbackend.application.order.OrderService
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.infra.Point
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.memberpoint.MemberPointRepository
import com.example.nokbackend.domain.order.OrderLineRepository
import com.example.nokbackend.domain.order.OrderRepository
import com.example.nokbackend.fixture.*
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class OrderServiceTest {
    @MockK
    private lateinit var orderService: OrderService

    @MockK
    private lateinit var orderRepository: OrderRepository

    @MockK
    private lateinit var orderLineRepository: OrderLineRepository

    @MockK
    private lateinit var gifticonRepository: GifticonRepository

    @MockK
    private lateinit var memberPointRepository: MemberPointRepository

    @MockK
    private lateinit var memberGifticonService: MemberGifticonService

    @MockK
    private lateinit var cartService: CartService


    @BeforeEach
    internal fun setUp() {
        orderService = OrderService(orderRepository, orderLineRepository, gifticonRepository, memberPointRepository, memberGifticonService, cartService)
    }

    @DisplayName("상품을 구매할 때")
    @Nested
    inner class RegisterOrder {

        private lateinit var member: Member
        private lateinit var orderRequest: OrderRequest

        private fun subject() {
            orderService.registerOrder(member, orderRequest)
        }

        @Test
        fun 성공한다() {
            member = aMember()
            orderRequest = aOrderRequest()

            every { gifticonRepository.findAllById(listOf(aGifticon().id)) } returns listOf(aGifticon())
            every { memberPointRepository.findByMemberId(member.id) } returns aMemberPoint()
            every { orderRepository.save(any()) } returns aOrder()
            every { orderLineRepository.save(any()) } returns aOrderLine()
            every { memberGifticonService.registerMemberGifticon(member, any()) } just Runs
            every { cartService.deleteItemFromCart(member, any()) } just Runs

            subject()
        }

        @Test
        fun 총_금액이_일치하지_않으면_실패한다() {
            member = aMember()
            orderRequest = aOrderRequest(totalPrice = Point(0))

            every { gifticonRepository.findAllById(listOf(aGifticon().id)) } returns listOf(aGifticon())
            every { memberPointRepository.findByMemberId(member.id) } returns aMemberPoint()
            every { orderRepository.save(any()) } returns aOrder()
            every { orderLineRepository.save(any()) } returns aOrderLine()
            every { memberGifticonService.registerMemberGifticon(member, any()) } just Runs
            every { cartService.deleteItemFromCart(member, any()) } just Runs

            assertThrows<IllegalStateException> {
                subject()
            }
        }

        @Test
        fun 상품이_존재하지_않으면_실패한다() {
            val gifticonId = 9999L
            member = aMember()
            orderRequest = aOrderRequest(orderLineRequests = listOf(aOrderLineRequest(gifticonId = gifticonId)))

            every { gifticonRepository.findAllById(listOf(gifticonId)) } returns listOf()
            every { memberPointRepository.findByMemberId(member.id) } returns aMemberPoint()
            every { orderRepository.save(any()) } returns aOrder()
            every { orderLineRepository.save(any()) } returns aOrderLine()
            every { memberGifticonService.registerMemberGifticon(member, any()) } just Runs
            every { cartService.deleteItemFromCart(member, any()) } just Runs

            assertThrows<RuntimeException> {
                subject()
            }
        }

        @Test
        fun 기프티콘_금액이_일치하지_않으면_실패한다() {
            val price = Point(333)
            member = aMember()
            orderRequest = aOrderRequest(orderLineRequests = listOf(aOrderLineRequest(price = price)))

            every { gifticonRepository.findAllById(listOf(aGifticon().id)) } returns listOf(aGifticon())
            every { memberPointRepository.findByMemberId(member.id) } returns aMemberPoint()
            every { orderRepository.save(any()) } returns aOrder()
            every { orderLineRepository.save(any()) } returns aOrderLine()
            every { memberGifticonService.registerMemberGifticon(member, any()) } just Runs

            assertThrows<IllegalStateException> {
                subject()
            }
        }
    }
}