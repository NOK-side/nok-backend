package com.example.nokbackend.application

import com.example.nokbackend.domain.BaseEntityUtil
import com.example.nokbackend.domain.cart.CartRepository
import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.member.findByMemberIdCheck
import com.example.nokbackend.domain.memberGifticon.MemberGifticon
import com.example.nokbackend.domain.memberGifticon.MemberGifticonRepository
import com.example.nokbackend.domain.memberGifticon.findByIdCheck
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class MemberGifticonService(
    private val memberGifticonRepository: MemberGifticonRepository,
    private val gifticonRepository: GifticonRepository,
    private val memberRepository: MemberRepository,
    private val cartRepository: CartRepository
) {

    fun findMyGifticon(member: Member): List<MemberGifticonResponse> {
        val memberGifticons = memberGifticonRepository.findByOwnerIdAndStatus(member.id, MemberGifticon.Status.ACTIVE)

        val gifticons = gifticonRepository.findAllById(memberGifticons.map { it.gifticonId })
        val gifticonMap = BaseEntityUtil<Gifticon>().mapById(gifticons)

        return memberGifticons.map {
            MemberGifticonResponse(gifticonMap[it.gifticonId]!!, it)
        }
    }

    fun buyGifticon(member: Member, buyGifticonRequest: BuyGifticonRequest) {
        // todo : 돈에 대한 로직 추가
        val gifticon = gifticonRepository.findByIdCheck(buyGifticonRequest.gifticonId)

        val memberGifticons = List(buyGifticonRequest.quantity) {
            MemberGifticon(
                ownerId = member.id,
                gifticonId = gifticon.id,
                dueDate = LocalDate.now().plusDays(gifticon.period)
            )
        }

        memberGifticonRepository.saveAll(memberGifticons)
    }

    fun buyGifticonInCart(member: Member, buyGifticonInCartRequest: BuyGifticonInCartRequest) {
        cartRepository.findAllById(buyGifticonInCartRequest.cartIds).forEach {
            check(it.ownerId == member.id) { "본인 카트의 상품만 구매할 수 있습니다" }
            val buyGifticonRequest = BuyGifticonRequest(it.gifticonId, it.quantity)
            buyGifticon(member, buyGifticonRequest)
            cartRepository.deleteById(it.id)
        }
    }

    fun sendGifticon(member: Member, sendGifticonRequest: SendGifticonRequest) {
        check(member.memberId != sendGifticonRequest.targetMemberId) { "본인에게 선물할 수 없습니다" }

        val memberGifticon = memberGifticonRepository.findByIdCheck(sendGifticonRequest.memberGifticonId)
        memberGifticon.validate(member)

        val gifticon = gifticonRepository.findByIdCheck(memberGifticon.gifticonId)
        val target = memberRepository.findByMemberIdCheck(sendGifticonRequest.targetMemberId)

        val transferredGifticon = memberGifticon.transferGifticonTo(target.id, LocalDate.now().plusDays(gifticon.period))

        memberGifticonRepository.save(transferredGifticon)
    }

    fun useGifticon(member: Member, useGifticonRequest: UseGifticonRequest) {
        val memberGifticon = memberGifticonRepository.findByIdCheck(useGifticonRequest.memberGifticonId)
        memberGifticon.validate(member)
        memberGifticon.use()
    }
}

