package com.example.nokbackend.application.mission

import com.example.nokbackend.application.util.CodeService
import com.example.nokbackend.application.geometry.GeometryService
import com.example.nokbackend.application.geometry.Point
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.memberGifticon.findByIdCheck
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.membermission.MemberMissionGroupRepository
import com.example.nokbackend.domain.membermission.MemberMissionRepository
import com.example.nokbackend.domain.misson.*
import com.example.nokbackend.domain.store.StoreRepository
import com.example.nokbackend.domain.store.findByIdCheck
import com.example.nokbackend.domain.touristspot.TouristSpotRepository
import com.example.nokbackend.domain.touristspot.findByIdCheck
import com.example.nokbackend.mapper.MissionMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MissionQueryService(
    private val missionGroupRepository: MissionGroupRepository,
    private val missionGroupQueryRepository: MissionGroupQueryRepository,
    private val missionRepository: MissionRepository,
    private val gifticonRepository: GifticonRepository,
    private val storeRepository: StoreRepository,
    private val memberMissionGroupRepository: MemberMissionGroupRepository,
    private val memberMissionRepository: MemberMissionRepository,
    private val geometryService: GeometryService,
    private val codeService: CodeService,
    private val touristSpotRepository: TouristSpotRepository,
    private val missionMapper: MissionMapper
) {
    fun findMyMission(member: Member): List<MissionGroupInfoResponse> {
        val memberMissionGroups = memberMissionGroupRepository.findByMemberId(member.id)

        val missionGroups = missionGroupRepository.findAllById(memberMissionGroups.map { it.missionGroupId })

        val gifticons = gifticonRepository.findAllById(missionGroups.map { it.prizeId })

        val stores = storeRepository.findAllById(gifticons.map { it.storeId })

        val missions = missionRepository.findByMissionGroupIn(missionGroups)

        val memberMissions = memberMissionRepository.findByMemberMissionGroupIn(memberMissionGroups)

        return missionMapper.toMissionGroupInfoResponses(missionGroups, gifticons, stores, memberMissionGroups, missions, memberMissions)
    }

    fun findMissionGroupInfo(member: Member, id: Long): MissionGroupInfoResponse {
        val missionGroup = missionGroupRepository.findByIdCheck(id)

        val gifticon = gifticonRepository.findByIdCheck(missionGroup.prizeId)

        val store = storeRepository.findByIdCheck(gifticon.storeId)

        val missions = missionRepository.findByMissionGroup(missionGroup)

        val memberMissionGroup = memberMissionGroupRepository.findByMissionGroupIdAndMemberId(missionGroup.id, member.id)

        val memberMissions = memberMissionGroup?.let { memberMissionRepository.findByMemberMissionGroup(it) } ?: listOf()

        return missionMapper.toMissionGroupInfoResponse(
            missionGroup = missionGroup,
            gifticon = gifticon,
            store = store,
            missions = missions,
            memberMissionGroup = memberMissionGroup,
            memberMissions = memberMissions
        )
    }

    fun findMissionGroupByCondition(member: Member, findMissionGroupCondition: FindMissionGroupCondition): List<MissionGroupInfoResponse> {
        val missionGroupIds = missionGroupRepository.findIdByDistance(
            longitude = findMissionGroupCondition.longitude,
            latitude = findMissionGroupCondition.latitude,
            meterDistance = findMissionGroupCondition.distance
        )

        val missionGroups = missionGroupQueryRepository.findByCondition(missionGroupIds, findMissionGroupCondition)

        val gifticons = gifticonRepository.findAllById(missionGroups.map { it.prizeId })

        val stores = storeRepository.findAllById(gifticons.map { it.storeId })

        val missions = missionRepository.findByMissionGroupIn(missionGroups)

        val memberMissionGroups = memberMissionGroupRepository.findByMissionGroupIdInAndMemberId(missionGroups.map { it.id }, member.id)

        val memberMissions = memberMissionRepository.findByMemberMissionGroupIn(memberMissionGroups)

        return missionMapper.toMissionGroupInfoResponses(missionGroups, gifticons, stores, memberMissionGroups, missions, memberMissions)
    }

    fun findMissionGroupOfTouristSpot(member: Member, id: Long): List<MissionGroupInfoResponse> {
        val touristSpot = touristSpotRepository.findByIdCheck(id)

        val findMissionGroupCondition = FindMissionGroupCondition(
            city = touristSpot.location.roadNameAddress
                .split(" ")
                .take(3)
                .reduce { acc, s -> "$acc $s" },
            keyword = null
        )

        return findMissionGroupByCondition(member, findMissionGroupCondition)
    }

    fun findCitiesOfMission(findCitiesRequest: FindCitiesRequest): List<FindCitiesResponse> {
        return missionGroupRepository.findByCityName(findCitiesRequest.city)
            .map { LocationAbbreviationWithLength(it, 2) }
            .groupBy { it.roadNameAddress }
            .entries
            .map { entry ->
                val point = geometryService.getCenterOfSpots(entry.value.map { Point(it.longitude.toDouble(), it.latitude.toDouble()) })
                FindCitiesResponse(
                    cityName = entry.key,
                    imageUrl = entry.value.random().imageUrl,
                    latitude = point.latitude.toBigDecimal(),
                    longitude = point.longitude.toBigDecimal()
                )
            }
    }

    fun getQRCodeFromQuestion(member: Member, missionId: Long): ByteArray {
        val mission = missionRepository.findByIdCheck(missionId)
        val exists = memberMissionGroupRepository.existsByMemberIdAndMissionGroupId(member.id, mission.missionGroup.id)

        check(exists) { "진행중인 미션이 존재하지 않습니다" }

        return codeService.createCodeImage(mission.questionUrl, CodeService.CodeType.QRCODE)
    }
}