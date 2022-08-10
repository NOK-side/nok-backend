package com.example.nokbackend.application

import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.store.StoreRepository
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

        val store = storeRepository.findByOwnerId(member.id) ?: throw RuntimeException("등록된 상점이 존재하지 않습니다")
        val gifticon = registerGifticonRequest.toEntity(store.id)
        gifticonRepository.save(gifticon)
    }

    fun findByCondition(findGifticonCondition: FindGifticonCondition): List<GifticonResponse> {
        return gifticonRepository.findAll()
            .filter { it.productName.contains(findGifticonCondition.name) }
            .map { GifticonResponse(it) }
            .reversed()
    }

    fun findGifticonInfo(id: Long): GifticonDetailResponse {
        val gifticon = gifticonRepository.findByIdCheck(id)
        return GifticonDetailResponse(gifticon)
    }
}