package com.example.nokbackend.domain.authentication

import org.springframework.data.jpa.repository.JpaRepository

interface AuthenticationRepository : JpaRepository<Authentication, Long> {

    fun findByTargetAndType(target: String, type: Authentication.Type): List<Authentication>

    fun findByIdAndTargetAndType(id: Long, target: String, type: Authentication.Type) : Authentication
}