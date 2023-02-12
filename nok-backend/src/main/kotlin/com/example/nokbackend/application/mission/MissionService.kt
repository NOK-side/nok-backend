package com.example.nokbackend.application.mission

import com.example.nokbackend.application.geometry.GeometryService
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.member.findByEmailCheck
import com.example.nokbackend.domain.membermission.*
import com.example.nokbackend.domain.misson.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import kotlin.math.abs

@Service
@Transactional
class MissionService(
    private val missionGroupRepository: MissionGroupRepository,
    private val missionGroupQueryRepository: MissionGroupQueryRepository,
    private val missionRepository: MissionRepository,
    private val memberMissionGroupRepository: MemberMissionGroupRepository,
    private val memberMissionRepository: MemberMissionRepository,
    private val geometryService: GeometryService,
    private val memberRepository: MemberRepository,
    private val resultOfMemberMissionQuestionRepository: ResultOfMemberMissionQuestionRepository,
) {


    fun startMission(member: Member, missionGroupId: Long) {
        val missionGroup = missionGroupRepository.findByIdCheck(missionGroupId)

        val isStartedMission = memberMissionGroupRepository.existsByMissionGroupIdAndMemberId(missionGroupId, member.id)
        check(!isStartedMission) { "이미 시작한 미션입니다" }

        val memberMissionGroup = MemberMissionGroup(
            memberId = member.id,
            missionGroupId = missionGroup.id,
            status = MemberMissionGroup.Status.PROCESS,
            dueDate = LocalDate.now().plusDays(7)
        )

        memberMissionGroupRepository.save(memberMissionGroup)

        val missions = missionRepository.findByMissionGroup(missionGroup)

        val memberMissions = missions.map {
            MemberMission(
                memberMissionGroup = memberMissionGroup,
                missionId = it.id,
                status = MemberMission.Status.PROGRESSING,
            )
        }

        memberMissionRepository.saveAll(memberMissions)
    }

    fun completeMissionTypeOfCurrentUserLocation(member: Member, memberMissionId: Long, currentLocation: DistanceFromLocation) {
        val memberMission = memberMissionRepository.findByIdCheck(memberMissionId)

        check(memberMission.memberMissionGroup.memberId == member.id) { "본인의 미션만 수행할 수 있습니다" }

        val mission = missionRepository.findByIdCheck(memberMission.missionId)

        check(mission.type == Mission.Type.CURRENT_USER_LOCATION) { "미션 타입이 다릅니다" }

        val distanceBetween = geometryService.getDistanceBetween(
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


    fun submitFromResult(formResult: FormResult) {
        println(formResult)
        val member = memberRepository.findByEmailCheck(formResult.email)
        val mission = missionRepository.findByFormIdCheck(formResult.formId)
        val memberMission = memberMissionRepository.findByMissionIdAndMemberMissionGroup_MemberIdCheck(mission.id, member.id)

        val score = formResult.results
            .map { it.score }
            .reduce { x, y -> x + y }

        val result = formResult.results
            .map { it.response }
            .reduce { x, y -> "$x | $y" }

        resultOfMemberMissionQuestionRepository.save(
            ResultOfMemberMissionQuestion(
                memberMission = memberMission,
                formId = formResult.formId,
                respondent = member.email,
                score = score,
                results = result
            )
        )

        if (score >= mission.qualification) {
            memberMission.complete()
        }
    }
}