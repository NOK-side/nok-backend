package com.example.nokbackend.application

import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.member.existByEmail
import com.example.nokbackend.domain.member.existByMemberId
import com.example.nokbackend.domain.member.findByMemberIdCheck
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

    fun generateTokenWithRegister(registerMemberRequest: RegisterMemberRequest): LoginResponse {
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
        memberRepository.save(member)
        val token = jwtTokenProvider.createToken(member.email)

        return LoginResponse(token, member.memberId, member.email, member.name, member.profileImg, member.role)
    }

    fun generateTokenWithLogin(loginRequest: LoginRequest): LoginResponse {
        val member = memberRepository.findByMemberIdCheck(loginRequest.memberId)
        member.authenticate(loginRequest.password)
        member.checkActivation()

        val token = jwtTokenProvider.createToken(member.email)

        return LoginResponse(token, member.memberId, member.email, member.name, member.profileImg, member.role)
    }
}
