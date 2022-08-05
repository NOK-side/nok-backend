package com.example.nokbackend.application

import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.authentication.AuthenticationRepository
import com.example.nokbackend.util.createRandomString
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class AuthenticationService(
    private val authenticationRepository: AuthenticationRepository
) {


    fun sendAuthenticationToEmail(email: String, type: Authentication.Type): AuthenticationResponse {
        val authentication = registerAuthentication(email, type)

//        mailService.sendMail(MailSendInfo(authentication.target, "이메일 검증", authentication.code))
        return AuthenticationResponse(authentication)
    }

    fun registerAuthentication(target: String, type: Authentication.Type): Authentication {
        authenticationRepository.findByTargetAndType(target, type)
            .forEach(Authentication::expired)

        return authenticationRepository.save(
            Authentication(
                target = target,
                type = type,
                expireDate = LocalDateTime.now().plusMinutes(DURATION),
                code = createRandomString(CODE_LENGTH)
            )
        )
    }

    // todo : 로직상 필요없을것 같음 -> 단순히 코드가 일치한지만들 판별 -> 정합성 체크에 성공한 인증코드는 더이상 살려둘 필요가 없음 -> confirm 처리 필요
    // todo : 실패의 경우 예외를 발생시키기에 confirm 되지 않으며 재인증 가능할것으로 보입
    fun check(confirmAuthenticationRequest: ConfirmAuthenticationRequest, type: Authentication.Type) {
        val (id, target, code) = confirmAuthenticationRequest
        authenticationRepository.findByIdAndTargetAndType(id, target, type)
            .validate(code)
    }

    fun confirmAuthentication(confirmAuthenticationRequest: ConfirmAuthenticationRequest, type: Authentication.Type) {
        val (id, target, code) = confirmAuthenticationRequest

        val authentication = authenticationRepository.findByIdAndTargetAndType(id, target, type)
        authentication.validate(code)
        authentication.confirm()
    }

    companion object {
        private const val DURATION: Long = 10L
        private const val CODE_LENGTH: Int = 6
    }

}



