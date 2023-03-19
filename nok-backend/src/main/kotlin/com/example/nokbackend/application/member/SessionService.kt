package com.example.nokbackend.application.member

import com.example.nokbackend.application.authentication.AuthenticationService
import com.example.nokbackend.application.authentication.ConfirmAuthenticationRequest
import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.member.*
import com.example.nokbackend.security.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SessionService(
    private val memberRepository: MemberRepository,
    private val authenticationService: AuthenticationService,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun generateTokenWithRegister(userAgent: String, registerMemberRequest: RegisterMemberRequest): LoginResponse {
        authenticationService.checkAuthentication(
            ConfirmAuthenticationRequest(
                registerMemberRequest.authenticationId,
                registerMemberRequest.email,
                registerMemberRequest.authenticationCode
            ),
            Authentication.Type.REGISTER
        )

        check(!memberRepository.existByMemberId(registerMemberRequest.memberId)) { "이미 등록된 아이디입니다" }
        check(!memberRepository.existByEmail(registerMemberRequest.email)) { "이미 등록된 이메일입니다" }

        val member = registerMemberRequest.toEntity()

        val accessToken = jwtTokenProvider.createAccessToken(member.email)
        val refreshToken = jwtTokenProvider.createRefreshToken(member.email)

        member.loginInformation = LoginInformation(userAgent, refreshToken, registerMemberRequest.fcmCode)

        memberRepository.save(member)

        return LoginResponse(member, TokenResponse(accessToken, refreshToken))
    }

    fun generateTokenWithLogin(userAgent: String, loginRequest: LoginRequest): LoginResponse {
        val member = memberRepository.findByMemberIdCheck(loginRequest.memberId)
        member.authenticate(loginRequest.password)
        member.checkActivation()

        val accessToken = jwtTokenProvider.createAccessToken(member.email)
        val refreshToken = jwtTokenProvider.createRefreshToken(member.email)

        member.loginInformation = LoginInformation(userAgent, refreshToken, loginRequest.fcmCode)

        return LoginResponse(member, TokenResponse(accessToken, refreshToken))
    }

    fun refreshToken(userAgent: String, refreshTokenRequest: RefreshTokenRequest): TokenResponse {
        check(jwtTokenProvider.isValidToken(refreshTokenRequest.refreshToken)) { "리스테리 토큰이 만료되었습니다" }

        val email = jwtTokenProvider.getEmail(refreshTokenRequest.refreshToken)
        val member = memberRepository.findByEmailCheck(email)

        check(refreshTokenRequest.refreshToken == member.loginInformation.refreshToken) { "리프레시 토큰 정보가 일치하지 않습니다" }

        val accessToken = jwtTokenProvider.createAccessToken(member.email)
        val refreshToken = jwtTokenProvider.createRefreshToken(member.email)

        member.loginInformation = LoginInformation(userAgent, refreshToken, refreshTokenRequest.fcmCode)

        return TokenResponse(accessToken, refreshToken)
    }
}
