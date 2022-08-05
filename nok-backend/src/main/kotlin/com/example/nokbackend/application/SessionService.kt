package com.example.nokbackend.application

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

    fun generateTokenWithRegister(registerMemberRequest: RegisterMemberRequest): String {
//TODO: 인증 코드 생성하는 부분 필요할 것 같습니다.
//        authenticationService.check(
//            ConfirmAuthenticationRequest(
//                registerMemberRequest.authenticationId,
//                registerMemberRequest.email,
//                registerMemberRequest.authenticationCode
//            ),
//            Authentication.Type.REGISTER
//        )

        val member = registerMemberRequest.toEntity()
        check(!memberRepository.existByMemberId(registerMemberRequest.memberId)) { "이미 등록된 아이디입니다" }
        check(!memberRepository.existByEmail(registerMemberRequest.email)) { "이미 등록된 이메일입니다" }
        memberRepository.save(member)

        return jwtTokenProvider.createToken(member.email)
    }

    fun generateTokenWithLogin(loginRequest: LoginRequest): String {
        val member = memberRepository.findByMemberIdCheck(loginRequest.memberId)
        member.authenticate(loginRequest.password)
        member.checkActivation()

        return jwtTokenProvider.createToken(member.email)
    }
}
