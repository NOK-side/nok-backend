package com.example.nokbackend.application.gifticon

import com.example.nokbackend.application.util.UUIDGenerator
import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.store.StoreRepository
import com.example.nokbackend.domain.store.findByIdCheck
import com.example.nokbackend.domain.store.findByOwnerIdCheck
import com.example.nokbackend.domain.toHashmapByIdAsKey
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
@Transactional
class GifticonService(
    private val gifticonRepository: GifticonRepository,
    private val storeRepository: StoreRepository,
    private val uuidGenerator: UUIDGenerator
) {
    fun registerGifticon(member: Member, registerGifticonRequest: RegisterGifticonRequest) {
        check(member.role == Member.Role.STORE) { "상점 주인만 등록할 수 있습니다" }

        val store = storeRepository.findByOwnerIdCheck(member.id)
        val gifticon = registerGifticonRequest.toEntity(store.id, generateGifticonId(store.id))
        gifticonRepository.save(gifticon)
    }

    fun findStoreGifticon(storeId: Long): List<GifticonResponse> {
        val gifticons = gifticonRepository.findByStoreIdAndStatus(storeId, Gifticon.Status.ACTIVE)

        val stores = storeRepository.findAllById(gifticons.map { it.storeId })
        val storeMap = toHashmapByIdAsKey(stores)

        return gifticons.map {
            val store = storeMap[it.storeId]!!
            GifticonResponse(it, store)
        }
    }

    fun findGifticonInfo(id: Long): GifticonDetailResponse {
        val gifticon = gifticonRepository.findByIdCheck(id)
        val store = storeRepository.findByIdCheck(gifticon.storeId)

        return GifticonDetailResponse(gifticon, store)
    }

    private fun generateGifticonId(storeId: Long): String {
        val pattern = DateTimeFormatter.ofPattern("yyyyMMdd")
        return "${storeId.toString().padStart(5, '0')} + ${LocalDate.now().format(pattern)} + ${uuidGenerator.generate(gifticonIdLength).uppercase()}"
    }

    companion object {
        private const val gifticonIdLength = 8
    }
}