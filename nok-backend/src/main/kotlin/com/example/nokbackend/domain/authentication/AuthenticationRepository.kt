package com.example.nokbackend.domain.authentication

import org.springframework.data.jpa.repository.JpaRepository
import java.lang.RuntimeException

fun AuthenticationRepository.findByIdAndTargetAndTypeCheck(id: Long, target: String, type: Authentication.Type) : Authentication = findByIdAndTargetAndType(id, target, type) ?: throw RuntimeException("인증코드가 존재하지 않습니다")

interface AuthenticationRepository : JpaRepository<Authentication, Long> {

    fun findByTargetAndType(target: String, type: Authentication.Type): List<Authentication>

    fun findByIdAndTargetAndType(id: Long, target: String, type: Authentication.Type) : Authentication?
}