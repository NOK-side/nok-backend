package com.example.nokbackend.domain.membermission

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import java.lang.RuntimeException

fun MemberMissionGroupRepository.findByIdCheck(id: Long): MemberMissionGroup = findByIdOrNull(id) ?: throw RuntimeException("멤버미션이 존재하지 않습니다")

interface MemberMissionGroupRepository : JpaRepository<MemberMissionGroup, Long> {

    fun findByMissionGroupIdAndMemberId(missionGroupId: Long, memberId: Long): MemberMissionGroup?

    fun findByMissionGroupIdInAndMemberId(missionGroupIds: List<Long>, memberId: Long): List<MemberMissionGroup>

    fun findByMemberIdAndStatus(memberId: Long, status: MemberMissionGroup.Status): List<MemberMissionGroup>

    fun existsByMemberIdAndMissionGroupId(memberId: Long, missionGroupId: Long): Boolean

    fun existsByMissionGroupIdAndMemberId(missionGroupId: Long, memberId: Long): Boolean
}