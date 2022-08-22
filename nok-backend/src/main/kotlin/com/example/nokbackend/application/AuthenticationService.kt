package com.example.nokbackend.application

import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.authentication.AuthenticationRepository
import com.example.nokbackend.domain.authentication.findByIdAndTargetAndTypeCheck
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class AuthenticationService(
    private val authenticationRepository: AuthenticationRepository,
    private val uuidGenerator: UUIDGenerator
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
                code = uuidGenerator.generate(CODE_LENGTH)
            )
        )
    }

    fun checkAuthentication(confirmAuthenticationRequest: ConfirmAuthenticationRequest, type: Authentication.Type) {
        val (id, target, code) = confirmAuthenticationRequest
        val authentication = authenticationRepository.findByIdAndTargetAndTypeCheck(id, target, type)
        authentication.verifyCode(code)
    }

    fun confirmAuthentication(confirmAuthenticationRequest: ConfirmAuthenticationRequest, type: Authentication.Type) {
        val (id, target, code) = confirmAuthenticationRequest

        val authentication = authenticationRepository.findByIdAndTargetAndTypeCheck(id, target, type)
        authentication.validate(code)
        authentication.confirm()
    }

    companion object {
        private const val DURATION: Long = 10L
        private const val CODE_LENGTH: Int = 6
    }

}



