package com.example.nokbackend.application.store

import com.example.nokbackend.application.authentication.AuthenticationService
import com.example.nokbackend.application.authentication.ConfirmAuthenticationRequest
import com.example.nokbackend.application.member.RegisterMemberRequest
import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.store.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StoreCommandService(
    private val storeRepository: StoreRepository,
    private val memberRepository: MemberRepository,
    private val storeImageRepository: StoreImageRepository,
    private val authenticationService: AuthenticationService
) {

    fun registerStore(registerStoreRequest: RegisterStoreRequest): RegisterStoreResponse {
        check(registerStoreRequest.owner.role == Member.Role.STORE) { "상점 주인으로만 등록할 수 있습니다" }

        authenticationService.confirmAuthentication(
            ConfirmAuthenticationRequest(
                registerStoreRequest.owner.authenticationId,
                registerStoreRequest.owner.email,
                registerStoreRequest.owner.authenticationCode
            ),
            Authentication.Type.REGISTER
        )

        val owner = saveOwner(registerStoreRequest.owner)

        val store = saveStore(registerStoreRequest, owner)

        saveStoreImages(registerStoreRequest.storeImages, store)

        return RegisterStoreResponse(store.id)
    }

    private fun saveOwner(registerStoreRequest: RegisterMemberRequest): Member {
        val owner = registerStoreRequest.toEntity()
        return memberRepository.save(owner)
    }

    private fun saveStore(registerStoreRequest: RegisterStoreRequest, owner: Member): Store {
        val store = registerStoreRequest.toEntity(owner.id)
        return storeRepository.save(store)
    }

    private fun saveStoreImages(storeImages: List<String>, store: Store) {
        storeImages.forEach {
            val storeImage = StoreImage(store, it, StoreImage.Status.ACTIVE)
            storeImageRepository.save(storeImage)
        }
    }

    fun updateStoreInformation(
        member: Member,
        storeId: Long,
        updateStoreInformationRequest: UpdateStoreInformationRequest
    ) {
        val store = storeRepository.findByIdCheck(storeId)

        check(store.ownerId == member.id) { "본인의 상점만 수정할 수 있습니다" }

        store.updateStoreInformation(updateStoreInformationRequest)
    }
}