package com.example.nokbackend.application

import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.store.MenuRepository
import com.example.nokbackend.domain.store.StoreImageRepository
import com.example.nokbackend.domain.store.StoreQueryRepository
import com.example.nokbackend.domain.store.StoreRepository
import com.example.nokbackend.fixture.*
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
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
    private lateinit var menuRepository: MenuRepository

    @MockK
    private lateinit var storeImageRepository: StoreImageRepository

    @MockK
    private lateinit var authenticationService: AuthenticationService

    @MockK
    private lateinit var imageService: ImageService

    @MockK
    private lateinit var storeService: StoreService

    @BeforeEach
    internal fun setUp() {
        storeService = StoreService(storeRepository, storeQueryRepository, memberRepository, menuRepository, storeImageRepository, authenticationService, imageService)
    }

    @Test
    fun 상점을_등록한다() {
        every { authenticationService.confirmAuthentication(any(), any()) } just Runs
        every { memberRepository.save(any()) } returns aMember().apply { role = Member.Role.STORE }
        every { storeRepository.save(any()) } returns aStore()
        every { menuRepository.save(any()) } returns aMenu()

        val registerStoreRequest = createRegisterStoreRequest()

        val storeId = storeService.registerStore(registerStoreRequest, listOf(), listOf(null))

        assertThat(storeId).isEqualTo(aStore().id)
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

        val registerMenuRequest = RegisterMenuRequest(
            name = menuName,
            price = menuPrice,
            description = menuDescription
        )

        return RegisterStoreRequest(ownerRequest, storeInformation, listOf(registerMenuRequest))
    }
}