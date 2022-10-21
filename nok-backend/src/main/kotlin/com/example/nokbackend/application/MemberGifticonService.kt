package com.example.nokbackend.application

import com.example.nokbackend.domain.cart.CartRepository
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.member.findByMemberIdCheck
import com.example.nokbackend.domain.memberGifticon.MemberGifticon
import com.example.nokbackend.domain.memberGifticon.MemberGifticonRepository
import com.example.nokbackend.domain.memberGifticon.findByIdCheck
import com.example.nokbackend.domain.store.StoreRepository
import com.example.nokbackend.domain.toHashmapByIdAsKey
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
@Transactional
class MemberGifticonService(
    private val memberGifticonRepository: MemberGifticonRepository,
    private val gifticonRepository: GifticonRepository,
    private val memberRepository: MemberRepository,
    private val cartRepository: CartRepository,
    private val storeRepository: StoreRepository,
    private val uuidGenerator: UUIDGenerator
) {

    fun findMyGifticon(member: Member): List<MemberGifticonResponse> {
        val memberGifticons = memberGifticonRepository.findByOwnerIdAndStatus(member.id, MemberGifticon.Status.ACTIVE)

        val gifticons = gifticonRepository.findAllById(memberGifticons.map { it.gifticonId })
        val gifticonMap = toHashmapByIdAsKey(gifticons)

        val stores = storeRepository.findAllById(gifticons.map { it.storeId })
        val storeMap = toHashmapByIdAsKey(stores)

        return memberGifticons.map {
            val gifticon = gifticonMap[it.gifticonId]!!
            val store = storeMap[gifticon.storeId]!!
            MemberGifticonResponse(gifticon, it, store)
        }
    }

    fun buyGifticon(member: Member, buyGifticonRequest: BuyGifticonRequest) {
        // todo : 돈에 대한 로직 추가
        val gifticon = gifticonRepository.findByIdCheck(buyGifticonRequest.gifticonId)

        val memberGifticons = List(buyGifticonRequest.quantity) {
            MemberGifticon(
                orderId = generateOrderId(),
                ownerId = member.id,
                gifticonId = gifticon.id,
                dueDate = LocalDate.now().plusDays(gifticon.period),
                orderCancellationDueDate = LocalDate.now().plusDays(gifticon.orderCancellationPeriod)
            )
        }

        memberGifticonRepository.saveAll(memberGifticons)
    }

    fun buyGifticonInCart(member: Member, buyGifticonInCartRequest: BuyGifticonInCartRequest) {
        val carts = cartRepository.findAllById(buyGifticonInCartRequest.cartIds)

        carts.forEach {
            check(it.ownerId == member.id) { "본인 카트의 상품만 구매할 수 있습니다" }

            val buyGifticonRequest = BuyGifticonRequest(it.gifticonId, it.quantity)
            buyGifticon(member, buyGifticonRequest)
        }

        cartRepository.deleteAllByIdInBatch(carts.map { it.id })
    }

    fun sendGifticon(member: Member, sendGifticonRequest: SendGifticonRequest) {
        check(member.memberId != sendGifticonRequest.targetMemberId) { "본인에게 선물할 수 없습니다" }

        val memberGifticon = memberGifticonRepository.findByIdCheck(sendGifticonRequest.memberGifticonId)
        memberGifticon.validate(member)

        val gifticon = gifticonRepository.findByIdCheck(memberGifticon.gifticonId)
        val target = memberRepository.findByMemberIdCheck(sendGifticonRequest.targetMemberId)

        val transferGifticonRequest = TransferGifticonRequest(target.id, LocalDate.now().plusDays(gifticon.period), generateOrderId(), LocalDate.now().plusDays(gifticon.orderCancellationPeriod))
        val transferredGifticon = memberGifticon.transferGifticonTo(transferGifticonRequest)

        memberGifticonRepository.save(transferredGifticon)
    }

    private fun generateOrderId(): String {
        val pattern = DateTimeFormatter.ofPattern("yyyyMMdd")
        return LocalDate.now().format(pattern) + uuidGenerator.generate(orderIdLength).uppercase()
    }

    fun useGifticon(member: Member, useGifticonRequest: UseGifticonRequest) {
        val memberGifticon = memberGifticonRepository.findByIdCheck(useGifticonRequest.memberGifticonId)
        memberGifticon.validate(member)
        memberGifticon.use()
    }

    companion object {
        private const val orderIdLength = 8
    }
}

