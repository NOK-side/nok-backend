package com.example.nokbackend.domain.membermission

import org.springframework.data.jpa.repository.JpaRepository

interface MemberMissionGroupRepository : JpaRepository<MemberMissionGroup, Long> {

    fun findByMissionGroupIdAndMemberId(missionGroupId: Long, memberId: Long): MemberMissionGroup?

    fun findByMissionGroupIdInAndMemberId(missionGroupIds: List<Long>, memberId: Long): List<MemberMissionGroup>

    fun findByMemberId(memberId: Long): List<MemberMissionGroup>

    fun existsByMemberIdAndMissionGroupId(memberId: Long, missionGroupId: Long): Boolean

    fun existsByMissionGroupIdAndMemberId(missionGroupId: Long, memberId: Long): Boolean
}