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

    fun create(target: String, type: Authentication.Type): Authentication {
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

    fun check(confirmAuthenticationRequest: ConfirmAuthenticationRequest, type: Authentication.Type) {
        val (id, target, code) = confirmAuthenticationRequest
        authenticationRepository.findByIdAndTargetAndType(id, target, type).validate(code)
    }

    fun confirm(confirmAuthenticationRequest: ConfirmAuthenticationRequest, type: Authentication.Type) {
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



