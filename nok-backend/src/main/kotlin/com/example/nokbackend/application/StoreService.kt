package com.example.nokbackend.application

import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.store.Store
import com.example.nokbackend.domain.store.StoreRepository
import com.example.nokbackend.domain.store.findByIdCheck
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StoreService(
    private val storeRepository: StoreRepository
) {

    fun registerStore(member: Member, registerStoreRequest: RegisterStoreRequest): Long {
        check(member.role == Member.Role.STORE) { "상점 주인만 등록할 수 있습니다" }
        val store = registerStoreRequest.toEntity(member.id)
        storeRepository.save(store)

        return store.id
    }

    fun findByCondition(findStoreCondition: FindStoreCondition): List<StoreResponse> {
        // todo : 동적쿼리 사용 필요??
        return storeRepository.findAll()
            .filter { it.name.contains(findStoreCondition.name.trim()) }
            .map { StoreResponse(it) }
            .reversed()
    }

    fun findStoreInfo(storeId: Long): StoreDetailResponse {
        val store = storeRepository.findByIdCheck(storeId)
        return StoreDetailResponse(store)
    }
}