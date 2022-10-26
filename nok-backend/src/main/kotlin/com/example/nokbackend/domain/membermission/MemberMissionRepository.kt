package com.example.nokbackend.domain.membermission

import org.springframework.data.jpa.repository.JpaRepository

interface MemberMissionRepository : JpaRepository<MemberMission, Long> {

    fun findByMemberMissionGroup(memberMissionGroup: MemberMissionGroup): List<MemberMission>

    fun findByMemberMissionGroupIn(memberMissionGroups: List<MemberMissionGroup>): List<MemberMission>
}