package com.example.nokbackend.application

import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.member.findByMemberIdCheck
import com.example.nokbackend.fixture.aMember
import com.example.nokbackend.fixture.memberId
import com.example.nokbackend.fixture.refreshToken
import com.example.nokbackend.security.JwtTokenProvider
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class SessionServiceTest {
    @MockK
    private lateinit var memberRepository: MemberRepository

    @MockK
    private lateinit var authenticationService: AuthenticationService

    @MockK
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @MockK
    private lateinit var sessionService: SessionService

    @BeforeEach
    internal fun setUp() {
        sessionService = SessionService(memberRepository, authenticationService, jwtTokenProvider)
    }


    @DisplayName("토큰 재발급은")
    @Nested
    inner class RefreshToken {
        private lateinit var userAgent: String
        private lateinit var refreshTokenRequest: RefreshTokenRequest

        private fun subject() {
            sessionService.refreshToken(userAgent, refreshTokenRequest)
        }

        @Test
        fun 유효한_리프레시_토큰을_보내면_성공한다() {
            userAgent = "user agent"
            refreshTokenRequest = RefreshTokenRequest(memberId, refreshToken)

            every { memberRepository.findByMemberIdCheck(any()) } returns aMember()
            every { jwtTokenProvider.isValidToken(refreshToken) } returns true
            every { jwtTokenProvider.createAccessToken(any()) } returns "valid access token"
            every { jwtTokenProvider.createRefreshToken(any()) } returns refreshToken

            subject()
        }

        @Test
        fun 만료된_리프레시_토큰을_보내면_실패한다() {
            userAgent = "user agent"
            refreshTokenRequest = RefreshTokenRequest(memberId, refreshToken)

            every { memberRepository.findByMemberIdCheck(any()) } returns aMember()
            every { jwtTokenProvider.isValidToken(refreshToken) } returns false
            every { jwtTokenProvider.createAccessToken(any()) } returns "valid access token"
            every { jwtTokenProvider.createRefreshToken(any()) } returns refreshToken

            assertThrows<IllegalStateException> {
                subject()
            }
        }

        @Test
        fun 보유한_리프레시_토큰과_일치하지_않으면_실패한다() {
            userAgent = "user agent"
            refreshTokenRequest = RefreshTokenRequest(memberId, "not same refresh token with member")

            every { memberRepository.findByMemberIdCheck(any()) } returns aMember()
            every { jwtTokenProvider.isValidToken(refreshToken) } returns false
            every { jwtTokenProvider.createAccessToken(any()) } returns "valid access token"
            every { jwtTokenProvider.createRefreshToken(any()) } returns refreshToken

            assertThrows<IllegalStateException> {
                subject()
            }
        }
    }
}