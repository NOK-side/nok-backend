package com.example.nokbackend.application

import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.authentication.AuthenticationService
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.member.existByEmail
import com.example.nokbackend.domain.member.existByMemberId
import com.example.nokbackend.domain.member.findByEmailCheck
import com.example.nokbackend.security.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberAuthenticationService(
    private val memberRepository: MemberRepository,
    private val authService: AuthenticationService,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun generateTokenWithRegister(registerMemberRequest: RegisterMemberRequest): String {
        authService.confirm(
            registerMemberRequest.authenticationId,
            registerMemberRequest.email,
            registerMemberRequest.authenticationCode,
            Authentication.Type.REGISTER
        )

        val member = registerMemberRequest.toEntity().apply {
            check(!memberRepository.existByMemberId(registerMemberRequest.memberId)) { "이미 등록된 아이디입니다" }
            check(!memberRepository.existByEmail(registerMemberRequest.email)) { "이미 등록된 이메일입니다" }
            memberRepository.save(this)
        }
        return jwtTokenProvider.createToken(member.email)
    }

    fun generateTokenWithLogin(loginRequest: LoginRequest): String {
        val member = memberRepository.findByEmailCheck(loginRequest.email).apply {
            authenticate(loginRequest.password)
            checkActivation()
        }

        return jwtTokenProvider.createToken(member.email)
    }

    fun confirmAuthenticationCode(confirmAuthenticationCodeRequest: ConfirmAuthenticationCodeRequest) {
        authService.confirm(
            confirmAuthenticationCodeRequest.id,
            confirmAuthenticationCodeRequest.email,
            confirmAuthenticationCodeRequest.code,
            Authentication.Type.REGISTER
        )
    }
}
