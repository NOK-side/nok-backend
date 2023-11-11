package com.example.nokbackend.application

import com.example.nokbackend.application.mail.MailEvent
import com.example.nokbackend.application.member.MemberService
import com.example.nokbackend.application.member.UpdateMemberRequest
import com.example.nokbackend.application.member.UpdatePasswordRequest
import com.example.nokbackend.application.util.UUIDGenerator
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.member.Password
import com.example.nokbackend.fixture.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
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
    private lateinit var uuidGenerator: UUIDGenerator

    @MockK
    private lateinit var memberService: MemberService

    @MockK
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @BeforeEach
    internal fun setUp() {
        memberService = MemberService(memberRepository, uuidGenerator, applicationEventPublisher)
        every { applicationEventPublisher.publishEvent(any<MailEvent>()) } just Runs
    }

    @Test
    fun 회원정보를_업데이트한다() {
        val member = aMember()
        val updateMemberRequest = UpdateMemberRequest(name = "updated name", phoneNumber = phoneNumber, profileImage = "")

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
        val member = aMember()

        every { uuidGenerator.generate(any()) } returns aUuid()

        val resetPassword = Password(aUuid())

        member.newPassword(resetPassword)

        assertThat(member.password).isEqualTo(resetPassword)
    }
}