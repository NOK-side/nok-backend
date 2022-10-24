package com.example.nokbackend.application

import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.membermission.MemberMissionGroupRepository
import com.example.nokbackend.domain.membermission.MemberMissionRepository
import com.example.nokbackend.domain.misson.*
import com.example.nokbackend.domain.store.StoreRepository
import com.example.nokbackend.domain.store.findByIdCheck
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MissionService(
    private val missionGroupRepository: MissionGroupRepository,
    private val missionRepository: MissionRepository,
    private val questionGroupRepository: QuestionGroupRepository,
    private val questionRepository: QuestionRepository,
    private val exampleRepository: ExampleRepository,
    private val gifticonRepository: GifticonRepository,
    private val storeRepository: StoreRepository,
    private val memberMissionGroupRepository: MemberMissionGroupRepository,
    private val memberMissionRepository: MemberMissionRepository
) {

    fun findMissionGroupInfo(member: Member, id: Long): MissionGroupInfoResponse {
        val missionGroup = missionGroupRepository.findByIdCheck(id)
        val gifticon = gifticonRepository.findByIdCheck(missionGroup.prizeId)
        val store = storeRepository.findByIdCheck(gifticon.storeId)
        val missions = missionRepository.findByMissionGroup(missionGroup)
        val memberMissionGroup = memberMissionGroupRepository.findByMissionGroupId(missionGroup.id)
        val memberMissions = memberMissionGroup?.let { memberMissionRepository.findByMemberMissionGroup(it) } ?: listOf()

        return MissionGroupInfoResponse(missionGroup, gifticon, store, missions, memberMissionGroup, memberMissions)
    }

    fun findMissionGroupOfTouristSpot(id: Long) {
        val missionGroups = missionGroupRepository.findByTouristSpotId(id)
    }


}