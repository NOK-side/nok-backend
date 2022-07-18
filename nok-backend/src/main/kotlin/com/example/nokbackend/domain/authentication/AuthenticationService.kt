package com.example.nokbackend.domain.authentication

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthenticationService(@Autowired val authRepo: AuthenticationRepository) {

    val durationMinute = 10L
    val codeLength = 6

    fun createAuthentication(target: String, type: Authentication.Type): Authentication {
        //TODO: 생성하기전에 기존 타겟으로 생성되어 있는 코드들 무효화가 맞을까??
        authRepo.saveAll(authRepo.findByTargetAndType(target, type).map(Authentication::expired))
        return authRepo.save(Authentication(
            target = target,
            type = type,
            expireDate = LocalDateTime.now().plusMinutes(durationMinute),
            code = createRandomString(codeLength)
        ))
    }

    fun checkAuthentication(target: String, code: String, type: Authentication.Type) {
        authRepo.findByTargetAndType(target, type);
    }


    private fun createRandomString(length: Int): String {
        val charList: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return (6..length)
            .map { kotlin.random.Random.nextInt(0, charList.size) }
            .map(charList::get)
            .joinToString("")
    }

}