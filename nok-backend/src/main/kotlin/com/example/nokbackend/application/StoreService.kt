package com.example.nokbackend.application

import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.store.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StoreService(
    private val storeRepository: StoreRepository,
    private val storeQueryRepository: StoreQueryRepository,
    private val memberRepository: MemberRepository,
    private val menuRepository: MenuRepository,
    private val authenticationService: AuthenticationService
) {

    fun registerStore(registerStoreRequest: RegisterStoreRequest): Long {
        check(registerStoreRequest.owner.role == Member.Role.STORE) { "상점 주인으로만 등록할 수 있습니다" }
        authenticationService.confirmAuthentication(
            ConfirmAuthenticationRequest(
                registerStoreRequest.owner.authenticationId,
                registerStoreRequest.owner.email,
                registerStoreRequest.owner.authenticationCode
            ),
            Authentication.Type.REGISTER
        )

        val owner = registerStoreRequest.owner.toEntity()
        memberRepository.save(owner)

        val store = registerStoreRequest.toEntity(owner.id)
        storeRepository.save(store)

        registerStoreRequest.menus.saveAllAsEntity(store)

        return store.id
    }

    fun findByCondition(findStoreCondition: FindStoreCondition): List<StoreResponse> {
        return storeQueryRepository.findByCondition(findStoreCondition)
            .map { StoreResponse(it) }
    }

    fun findStoreInfo(storeId: Long): StoreDetailResponse {
        val store = storeRepository.findByIdCheck(storeId)

        check(store.status == Store.Status.ACTIVE) { "삭제되거나 등록대기 상태인 점포입니다" }

        return StoreDetailResponse(store)
    }

    private fun List<RegisterMenuRequest>.saveAllAsEntity(store: Store) =
        forEach {
            val menu = it.toEntity(store)
            menuRepository.save(menu)
        }
}