package com.example.nokbackend.application.mission

import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.membermission.MemberMissionGroup
import com.example.nokbackend.domain.membermission.MemberMissionGroupRepository
import com.example.nokbackend.domain.membermission.MemberMissionRepository
import com.example.nokbackend.domain.misson.MissionGroupRepository
import com.example.nokbackend.domain.misson.MissionRepository
import com.example.nokbackend.domain.store.StoreRepository
import com.example.nokbackend.mapper.MissionMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberMissionQueryService(
    private val missionGroupRepository: MissionGroupRepository,
    private val missionRepository: MissionRepository,
    private val memberMissionGroupRepository: MemberMissionGroupRepository,
    private val memberMissionRepository: MemberMissionRepository,
    private val storeRepository: StoreRepository,
    private val gifticonRepository: GifticonRepository,
    private val missionMapper: MissionMapper,
) {

    fun findMyMission(member: Member, status: MemberMissionGroup.Status): List<MissionGroupInfoResponse> {
        val memberMissionGroups =
            memberMissionGroupRepository.findByMemberIdAndStatus(member.id, status)

        val missionGroups = missionGroupRepository.findAllById(memberMissionGroups.map { it.missionGroupId })

        val gifticons = gifticonRepository.findAllById(missionGroups.map { it.prizeId })

        val stores = storeRepository.findAllById(gifticons.map { it.storeId })

        val missions = missionRepository.findByMissionGroupIn(missionGroups)

        val memberMissions = memberMissionRepository.findByMemberMissionGroupIn(memberMissionGroups)

        return missionMapper.toMissionGroupInfoResponses(
            missionGroups,
            gifticons,
            stores,
            memberMissionGroups,
            missions,
            memberMissions
        )
    }
}