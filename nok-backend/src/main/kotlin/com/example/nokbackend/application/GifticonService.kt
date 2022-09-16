package com.example.nokbackend.application

import com.example.nokbackend.domain.model.gifticon.Gifticon
import com.example.nokbackend.domain.model.gifticon.GifticonRepository
import com.example.nokbackend.domain.model.gifticon.findByIdCheck
import com.example.nokbackend.domain.model.member.Member
import com.example.nokbackend.domain.model.store.StoreRepository
import com.example.nokbackend.domain.model.store.findByOwnerIdCheck

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GifticonService(
    private val gifticonRepository: GifticonRepository,
    private val storeRepository: StoreRepository
) {
    fun registerGifticon(member: Member, registerGifticonRequest: RegisterGifticonRequest) {
        check(member.role == Member.Role.STORE) { "상점 주인만 등록할 수 있습니다" }

        val store = storeRepository.findByOwnerIdCheck(member.id)
        val gifticon = registerGifticonRequest.toEntity(store.id)
        gifticonRepository.save(gifticon)
    }

    fun findStoreGifticon(storeId: Long): List<GifticonResponse> {
        return gifticonRepository.findByStoreIdAndStatus(storeId, Gifticon.Status.ACTIVE)
            .map { GifticonResponse(it) }
    }

    fun findGifticonInfo(id: Long): GifticonDetailResponse {
        val gifticon = gifticonRepository.findByIdCheck(id)
        return GifticonDetailResponse(gifticon)
    }
}