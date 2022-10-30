package com.example.nokbackend.application

import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.membermission.*
import com.example.nokbackend.domain.misson.*
import com.example.nokbackend.domain.store.Store
import com.example.nokbackend.domain.store.StoreRepository
import com.example.nokbackend.domain.store.findByIdCheck
import com.example.nokbackend.domain.toHashmapByIdAsKey
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.abs

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
    private val memberMissionRepository: MemberMissionRepository,
    private val distanceService: DistanceService
) {

    fun findMissionGroupInfo(member: Member, id: Long): MissionGroupInfoResponse {
        val missionGroup = missionGroupRepository.findByIdCheck(id)

        val gifticon = gifticonRepository.findByIdCheck(missionGroup.prizeId)

        val store = storeRepository.findByIdCheck(gifticon.storeId)

        val missions = missionRepository.findByMissionGroup(missionGroup)

        val memberMissionGroup = memberMissionGroupRepository.findByMissionGroupId(missionGroup.id)

        val memberMissions = memberMissionGroup?.let { memberMissionRepository.findByMemberMissionGroup(it) } ?: listOf()

        return MissionGroupInfoResponse(
            missionGroup = missionGroup,
            gifticon = gifticon,
            store = store,
            missions = missions,
            memberMissionGroup = memberMissionGroup,
            memberMissions = memberMissions
        )
    }

    fun findMissionGroupOfTouristSpot(member: Member, id: Long): List<MissionGroupInfoResponse> {
        val missionGroups = missionGroupRepository.findByTouristSpotId(id)

        val gifticons = gifticonRepository.findAllById(missionGroups.map { it.prizeId })

        val stores = storeRepository.findAllById(gifticons.map { it.storeId })

        val missions = missionRepository.findByMissionGroupIn(missionGroups)

        val memberMissionGroups = memberMissionGroupRepository.findByMissionGroupIdIn(missionGroups.map { it.id })

        val memberMissions = memberMissionRepository.findByMemberMissionGroupIn(memberMissionGroups)

        return createMissionGroupInfoResponses(missionGroups, gifticons, stores, memberMissionGroups, missions, memberMissions)
    }

    fun findMissionGroupByDistance(member: Member, distanceFromLocation: DistanceFromLocation): List<MissionGroupInfoResponse> {
        val missionGroups = missionGroupRepository.findByDistance(
            longitude = distanceFromLocation.longitude,
            latitude = distanceFromLocation.latitude,
            meterDistance = distanceFromLocation.distance
        )

        val gifticons = gifticonRepository.findAllById(missionGroups.map { it.prizeId })

        val stores = storeRepository.findAllById(gifticons.map { it.storeId })

        val missions = missionRepository.findByMissionGroupIn(missionGroups)

        val memberMissionGroups = memberMissionGroupRepository.findByMissionGroupIdIn(missionGroups.map { it.id })

        val memberMissions = memberMissionRepository.findByMemberMissionGroupIn(memberMissionGroups)

        return createMissionGroupInfoResponses(missionGroups, gifticons, stores, memberMissionGroups, missions, memberMissions)
    }

    fun findMyMission(member: Member): List<MissionGroupInfoResponse> {
        val memberMissionGroups = memberMissionGroupRepository.findByMemberId(member.id)

        val missionGroups = missionGroupRepository.findAllById(memberMissionGroups.map { it.missionGroupId })

        val gifticons = gifticonRepository.findAllById(missionGroups.map { it.prizeId })

        val stores = storeRepository.findAllById(gifticons.map { it.storeId })

        val missions = missionRepository.findByMissionGroupIn(missionGroups)

        val memberMissions = memberMissionRepository.findByMemberMissionGroupIn(memberMissionGroups)

        return createMissionGroupInfoResponses(missionGroups, gifticons, stores, memberMissionGroups, missions, memberMissions)
    }

    private fun createMissionGroupInfoResponses(
        missionGroups: List<MissionGroup>,
        gifticons: List<Gifticon>,
        stores: List<Store>,
        memberMissionGroups: List<MemberMissionGroup>,
        missions: List<Mission>,
        memberMissions: List<MemberMission>
    ) = missionGroups.map {
        val gifticon = toHashmapByIdAsKey(gifticons)[it.prizeId]!!
        val store = toHashmapByIdAsKey(stores)[gifticon.id]!!
        val memberMissionGroup = memberMissionGroups.firstOrNull { memberMissionGroup -> memberMissionGroup.missionGroupId == it.id }

        MissionGroupInfoResponse(
            missionGroup = it,
            gifticon = gifticon,
            store = store,
            missions = missions.filter { mission -> mission.missionGroup == it },
            memberMissionGroup = memberMissionGroup,
            memberMissions = memberMissions.filter { memberMission -> memberMission.memberMissionGroup == memberMissionGroup }
        )
    }

    fun startMission(member: Member, missionGroupId: Long) {
        val missionGroup = missionGroupRepository.findByIdCheck(missionGroupId)

        val isStartedMission = memberMissionGroupRepository.existsByMissionGroupId(missionGroupId)
        check(!isStartedMission) { "이미 시작한 미션입니다" }

        val memberMissionGroup = MemberMissionGroup(
            memberId = member.id,
            missionGroupId = missionGroup.id,
            MemberMissionGroup.Status.PROCESS
        )

        memberMissionGroupRepository.save(memberMissionGroup)

        val missions = missionRepository.findByMissionGroup(missionGroup)

        val memberMissions = missions.map {
            MemberMission(
                memberMissionGroup = memberMissionGroup,
                missionId = it.id,
                status = MemberMission.Status.PROGRESSING
            )
        }

        memberMissionRepository.saveAll(memberMissions)
    }

    fun completeMissionTypeOfCurrentUserLocation(member: Member, memberMissionId: Long, currentLocation: DistanceFromLocation) {
        val memberMission = memberMissionRepository.findByIdCheck(memberMissionId)

        check(memberMission.memberMissionGroup.memberId == member.id) { "본인의 미션만 수행할 수 있습니다" }

        val mission = missionRepository.findByIdCheck(memberMission.missionId)

        check(mission.type == Mission.Type.CURRENT_USER_LOCATION) { "미션 타입이 다릅니다" }

        val distanceBetween = distanceService.getDistanceBetween(
            longitude1 = currentLocation.longitude.toDouble(),
            latitude1 = currentLocation.latitude.toDouble(),
            longitude2 = mission.location.longitude.toDouble(),
            latitude2 = mission.location.latitude.toDouble()
        )

        check(distanceBetween <= mission.qualification) { "목표한 위치에 도달하지 못하였습니다 : ${abs(distanceBetween - mission.qualification)}미터 차이" }

        memberMission.complete()

        val memberMissions = memberMissionRepository.findByMemberMissionGroup(memberMission.memberMissionGroup)

        if (!memberMissions.any { it.status != MemberMission.Status.FINISHED }) {
            memberMission.memberMissionGroup.complete()
        }
    }
}