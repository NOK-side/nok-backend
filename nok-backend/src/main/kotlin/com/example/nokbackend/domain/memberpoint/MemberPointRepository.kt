package com.example.nokbackend.domain.memberpoint

import com.example.nokbackend.domain.infra.Point
import org.springframework.data.jpa.repository.JpaRepository

fun MemberPointRepository.findByMemberIdCheck(memberId: Long): MemberPoint = findByMemberId(memberId)
    ?: save(MemberPoint(memberId, Point(0)))

interface MemberPointRepository : JpaRepository<MemberPoint, Long> {
    fun findByMemberId(memberId: Long): MemberPoint?
}