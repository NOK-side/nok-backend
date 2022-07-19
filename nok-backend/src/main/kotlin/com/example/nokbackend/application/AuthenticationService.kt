package com.example.nokbackend.application

import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.authentication.AuthenticationRepository
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

    fun confirm(confirmAuthenticationRequest: ConfirmAuthenticationRequest, type: Authentication.Type) {
        val (id, target, code) = confirmAuthenticationRequest

        authenticationRepository.findByIdAndTargetAndType(id, target, type).run {
            validate(code)
            confirm()
        }
    }

    companion object {
        private const val DURATION: Long = 10L
        private const val CODE_LENGTH: Int = 6
    }

}

fun createRandomString(length: Int): String {
    val charList: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    return (0 until length)
        .map { kotlin.random.Random.nextInt(0, charList.size) }
        .map(charList::get)
        .joinToString("")
}

