package com.example.nokbackend.application

import com.example.nokbackend.application.authentication.AuthenticationService
import com.example.nokbackend.application.member.RegisterMemberRequest
import com.example.nokbackend.application.store.RegisterStoreRequest
import com.example.nokbackend.application.store.StoreService
import com.example.nokbackend.application.store.UpdateStoreInformationRequest
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.store.*
import com.example.nokbackend.fixture.*
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class StoreServiceTest {
    @MockK
    private lateinit var storeRepository: StoreRepository

    @MockK
    private lateinit var storeQueryRepository: StoreQueryRepository

    @MockK
    private lateinit var memberRepository: MemberRepository

    @MockK
    private lateinit var storeImageRepository: StoreImageRepository

    @MockK
    private lateinit var authenticationService: AuthenticationService

    @MockK
    private lateinit var storeService: StoreService

    @BeforeEach
    internal fun setUp() {
        storeService = StoreService(storeRepository, storeQueryRepository, memberRepository, storeImageRepository, authenticationService)
    }

    @Test
    fun 상점을_등록한다() {
        every { authenticationService.confirmAuthentication(any(), any()) } just Runs
        every { memberRepository.save(any()) } returns aMember().apply { role = Member.Role.STORE }
        every { storeRepository.save(any()) } returns aStore()
        every { storeImageRepository.save(any()) } returns aStoreImage()

        val registerStoreRequest = createRegisterStoreRequest()
        val storeResponse = storeService.registerStore(registerStoreRequest)

        assertThat(storeResponse.storeId).isEqualTo(aStore().id)
    }

    private fun createRegisterStoreRequest(): RegisterStoreRequest {
        val ownerRequest = RegisterMemberRequest(
            memberId = memberId,
            email = email,
            name = name,
            phoneNumber = phoneNumber,
            profileImage = "",
            password = password,
            role = Member.Role.STORE,
            authenticationId = 1L,
            authenticationCode = ""
        )

        val storeInformation = aStoreInformation()

        return RegisterStoreRequest(ownerRequest, storeInformation, listOf())
    }

    @Test
    @DisplayName("상점정보 업데이트에 성공한다")
    fun updateStoreInformation() {
        val member = aMember()
        val store = aStore()
        val updateStoreInformationRequest = UpdateStoreInformationRequest(
            aStoreInformation(
                name = "new store name",
            )
        )

        every { storeRepository.findByIdCheck(any()) } returns store

        storeService.updateStoreInformation(member, aStore().id, updateStoreInformationRequest)

        assertThat(store.storeInformation).isEqualTo(updateStoreInformationRequest.storeInformation)
    }

}