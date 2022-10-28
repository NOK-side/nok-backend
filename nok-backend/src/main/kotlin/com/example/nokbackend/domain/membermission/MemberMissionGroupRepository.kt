package com.example.nokbackend.domain.membermission

import org.springframework.data.jpa.repository.JpaRepository

interface MemberMissionGroupRepository : JpaRepository<MemberMissionGroup, Long> {

    fun findByMissionGroupId(missionGroupId: Long): MemberMissionGroup?

    fun findByMissionGroupIdIn(missionGroupIds: List<Long>): List<MemberMissionGroup>

    fun findByMemberId(memberId: Long): List<MemberMissionGroup>

    fun existsByMissionGroupId(missionGroupId: Long): Boolean
}