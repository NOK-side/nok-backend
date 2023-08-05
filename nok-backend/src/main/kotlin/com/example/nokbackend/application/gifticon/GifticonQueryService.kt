package com.example.nokbackend.application.gifticon

import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.store.StoreRepository
import com.example.nokbackend.domain.store.findByIdCheck
import com.example.nokbackend.domain.toHashmapByIdAsKey
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GifticonQueryService(
    private val gifticonRepository: GifticonRepository,
    private val storeRepository: StoreRepository
) {

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
}