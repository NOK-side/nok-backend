package com.example.nokbackend.domain.authentication

import org.springframework.data.jpa.repository.JpaRepository

fun AuthenticationRepository.findLastReadyAuthenticationByEmail(email: String): Authentication = findByTargetEmailAndStatus(email, Authentication.Status.READY).lastOrNull() ?: throw RuntimeException("유효한 인증코드가 존재하지 않습니다")

fun AuthenticationRepository.findByTargetEmailAndKeyCheck(targetEmail: String, code: String): Authentication = findByTargetEmailAndCode(targetEmail, code) ?: throw RuntimeException("인증정보가 존재하지 않습니다")

interface AuthenticationRepository : JpaRepository<Authentication, Long> {

    fun findByTargetEmailAndStatus(targetEmail: String, status: Authentication.Status): List<Authentication>

    fun findByTargetEmailAndCode(targetEmail: String, code: String): Authentication?
}