package com.example.nokbackend.domain.member

import org.springframework.data.jpa.repository.JpaRepository

fun MemberRepository.findByEmail(email: String): Member? = findByInformationEmail(email)

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByInformationEmail(email: String): Member?
}