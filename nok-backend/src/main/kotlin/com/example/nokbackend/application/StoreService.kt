package com.example.nokbackend.application

import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.store.MenuRepository
import com.example.nokbackend.domain.store.Store
import com.example.nokbackend.domain.store.StoreRepository
import com.example.nokbackend.domain.store.findByIdCheck
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StoreService(
    private val storeRepository: StoreRepository,
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

        registerStoreRequest.menus.saveMenuOfStore(store)

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

    private fun List<RegisterMenuRequest>.saveMenuOfStore(store: Store) =
        forEach {
            val menu = it.toEntity(store)
            menuRepository.save(menu)
        }
}