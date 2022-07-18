package com.example.nokbackend.domain.authentication

import org.springframework.data.jpa.repository.JpaRepository
interface AuthenticationRepository : JpaRepository<Authentication, Long> {

    fun findByTargetAndType(target: String, type: Authentication.Type): List<Authentication>

    fun findByTargetAndStatus(target: String, status: Authentication.Status): List<Authentication>

    fun findByTargetAndCode(target: String, code: String): Authentication?
}