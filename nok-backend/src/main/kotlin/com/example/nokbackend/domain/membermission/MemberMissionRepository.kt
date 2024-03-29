package com.example.nokbackend.domain.membermission

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun MemberMissionRepository.findByIdCheck(id: Long) : MemberMission = findByIdOrNull(id) ?: throw RuntimeException("멤버 미션이 존재하지 않습니다")

fun MemberMissionRepository.findByMissionIdAndMemberMissionGroup_MemberIdCheck(missionId: Long, memberId: Long): MemberMission = findByMissionIdAndMemberMissionGroup_MemberId(missionId, memberId) ?: throw RuntimeException("멤버 미션이 존재하지 않습니다")


interface MemberMissionRepository : JpaRepository<MemberMission, Long> {

    fun findByMemberMissionGroup(memberMissionGroup: MemberMissionGroup): List<MemberMission>

    fun findByMemberMissionGroupIn(memberMissionGroups: List<MemberMissionGroup>): List<MemberMission>

    fun findByMissionIdAndMemberMissionGroup_MemberId(missionId: Long, memberId: Long): MemberMission?
}