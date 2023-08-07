package com.example.nokbackend.application.gifticon

import com.example.nokbackend.application.util.UUIDGenerator
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.member.findByMemberIdCheck
import com.example.nokbackend.domain.membergifticon.MemberGifticon
import com.example.nokbackend.domain.membergifticon.MemberGifticonRepository
import com.example.nokbackend.domain.membergifticon.findByIdCheck
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
@Transactional
class MemberGifticonCommandService(
    private val memberGifticonRepository: MemberGifticonRepository,
    private val gifticonRepository: GifticonRepository,
    private val memberRepository: MemberRepository,
    private val uuidGenerator: UUIDGenerator,
    private val orderIdLength: Int = 8
) {

    fun registerMemberGifticon(member: Member, buyGifticonRequest: BuyGifticonRequest) {
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

    fun sendGifticon(member: Member, sendGifticonRequest: SendGifticonRequest) {
        check(member.memberId != sendGifticonRequest.targetMemberId) { "본인에게 선물할 수 없습니다" }

        val memberGifticon = memberGifticonRepository.findByIdCheck(sendGifticonRequest.memberGifticonId)
        memberGifticon.validate(member)

        val gifticon = gifticonRepository.findByIdCheck(memberGifticon.gifticonId)
        val target = memberRepository.findByMemberIdCheck(sendGifticonRequest.targetMemberId)

        val transferGifticonRequest = TransferGifticonRequest(
            target.id,
            LocalDate.now().plusDays(gifticon.period),
            generateOrderId(),
            LocalDate.now().plusDays(gifticon.orderCancellationPeriod)
        )
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
}

