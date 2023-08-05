package com.example.nokbackend.application.gifticon

import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.membergifticon.MemberGifticon
import com.example.nokbackend.domain.membergifticon.MemberGifticonRepository
import com.example.nokbackend.domain.membergifticon.findByIdCheck
import com.example.nokbackend.domain.store.StoreRepository
import com.example.nokbackend.domain.store.findByIdCheck
import com.example.nokbackend.domain.toHashmapByIdAsKey
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberGifticonQueryService(
    private val memberGifticonRepository: MemberGifticonRepository,
    private val gifticonRepository: GifticonRepository,
    private val storeRepository: StoreRepository
) {

    fun findMyGifticon(member: Member, status: MemberGifticon.Status): List<MemberGifticonResponse> {
        val memberGifticons = memberGifticonRepository.findByOwnerIdAndStatus(member.id, status)

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


    fun findMemberGifticonInfo(member: Member, memberGifticonId: Long): MemberGifticonResponse {
        val memberGifticon = memberGifticonRepository.findByIdCheck(memberGifticonId)

        check(memberGifticon.ownerId == member.id) { "본인의 기프티콘만 조회할 수 있습니다" }

        val gifticon = gifticonRepository.findByIdCheck(memberGifticon.gifticonId)
        val store = storeRepository.findByIdCheck(gifticon.storeId)

        return MemberGifticonResponse(gifticon, memberGifticon, store)
    }
}