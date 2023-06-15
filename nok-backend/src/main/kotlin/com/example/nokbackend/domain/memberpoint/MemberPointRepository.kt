package com.example.nokbackend.domain.memberpoint

import org.springframework.data.jpa.repository.JpaRepository

interface MemberPointRepository : JpaRepository<MemberPoint, Long> {

    fun findByMemberId(memberId: Long) : MemberPoint?
}