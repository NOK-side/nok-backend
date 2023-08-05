package com.example.nokbackend.application.store

import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.member.findByIdCheck
import com.example.nokbackend.domain.store.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StoreQueryService(
    private val storeRepository: StoreRepository,
    private val storeQueryRepository: StoreQueryRepository,
    private val memberRepository: MemberRepository,
    private val storeImageRepository: StoreImageRepository
) {

    fun findByCondition(findStoreCondition: FindStoreCondition): List<StoreResponse> {
        val storeIds = storeRepository.findStoreIdsByDistance(
            longitude = findStoreCondition.longitude,
            latitude = findStoreCondition.latitude,
            meterDistance = findStoreCondition.distance
        )

        val stores = storeQueryRepository.findByCondition(storeIds, findStoreCondition)
        val images = storeImageRepository.findByStoreInAndStatus(stores, StoreImage.Status.ACTIVE)
        val imageMap = images.groupBy { it.store.id }

        return stores.map { StoreResponse(it, imageMap[it.id]?.first()) }
    }

    fun findStoreInformation(storeId: Long): StoreDetailResponse {
        val store = storeRepository.findByIdAndStatusCheck(storeId, Store.Status.ACTIVE)

        val storeImages = storeImageRepository.findByStoreAndStatus(store, StoreImage.Status.ACTIVE)

        val owner = memberRepository.findByIdCheck(store.ownerId)

        return StoreDetailResponse(store, storeImages, owner)
    }
}