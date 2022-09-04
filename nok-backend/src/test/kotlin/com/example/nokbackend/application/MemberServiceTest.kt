package com.example.nokbackend.application

import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.member.Password
import com.example.nokbackend.domain.member.findByMemberIdCheck
import com.example.nokbackend.fixture.aMember
import com.example.nokbackend.fixture.aUuid
import com.example.nokbackend.fixture.email
import com.example.nokbackend.fixture.password
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher

@ExtendWith(MockKExtension::class)
class MemberServiceTest {
    @MockK
    private lateinit var memberRepository: MemberRepository

    @MockK
    private lateinit var authenticationService: AuthenticationService

    @MockK
    private lateinit var imageService: ImageService

    @MockK
    private lateinit var uuidGenerator: UUIDGenerator

    @MockK
    private lateinit var memberService: MemberService

    @MockK
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @BeforeEach
    internal fun setUp() {
        memberService = MemberService(memberRepository, authenticationService, imageService, uuidGenerator, applicationEventPublisher)
        every { applicationEventPublisher.publishEvent(any<MailEvent>()) } just Runs
    }

    @Test
    fun 회원정보를_업데이트한다() {
        val member = aMember()
        val updateMemberRequest = UpdateMemberRequest(name = "updated name", imageUrl = "", verificationPassword = password)

        memberService.updateMemberInfo(member, updateMemberRequest)

        assertThat(member.information.name).isEqualTo(updateMemberRequest.name)
    }

    @Test
    fun 비밀번호를_업데이트한다() {
        val member = aMember()
        val newPassword = Password("new password")
        val updatePasswordRequest = UpdatePasswordRequest(password, newPassword)

        memberService.updatePassword(member, updatePasswordRequest)

        assertThat(member.password).isEqualTo(newPassword)
    }

    @Test
    fun 비밀번호를_랜덤값으로_초기화한다() {
//        every { uuidGenerator.generate(any()) } returns uuid
//        every { authenticationService.confirmAuthentication(any(), any()) } just Runs
//        every { memberRepository.findByMemberIdCheck(any()) } returns aMember()
//        every { memberRepository.save(any()) } returns aMember()
//
//        val resetMemberPasswordRequest = ResetMemberPasswordRequest( email)
//        val resetMemberPassword = memberService.resetMemberPassword(resetMemberPasswordRequest)
//
//        assertThat(resetMemberPassword.password).isEqualTo(Password(uuid))
    }
}