package com.example.nokbackend.application

import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.memberGifticon.MemberGifticon
import com.example.nokbackend.domain.memberGifticon.MemberGifticonRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class MemberGifticonService(
    private val memberGifticonRepository: MemberGifticonRepository,
    private val gifticonRepository: GifticonRepository
) {

    fun findMyGifticon(member: Member): List<MemberGifticonResponse> {
        val memberGifticons = memberGifticonRepository.findByOwnerIdAndStatus(member.id, MemberGifticon.Status.ACTIVE)
        val gifticonMap = gifticonRepository.findAllById(memberGifticons.map { it.gifticonId })
            .mapById()

        return memberGifticons.map {
            MemberGifticonResponse(gifticonMap[it.gifticonId]!!, it)
        }
    }

    private fun List<Gifticon>.mapById(): HashMap<Long, Gifticon> {
        val gifticonMap = hashMapOf<Long, Gifticon>()

        this.forEach { gifticonMap[it.id] = it }

        return gifticonMap
    }

    fun buyGifticon(member: Member, buyGifticonRequest: BuyGifticonRequest) {
        // todo : 돈에 대한 로직 추가
        val gifticon = gifticonRepository.findByIdCheck(buyGifticonRequest.gifticonId)

        val memberGifticons = List(buyGifticonRequest.quantity) {
            MemberGifticon(
                ownerId = member.id,
                gifticonId = gifticon.id,
                dueDate = LocalDate.now().plusDays(gifticon.period.toLong())
            )
        }

        memberGifticonRepository.saveAll(memberGifticons)
    }
}

