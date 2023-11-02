package com.example.nokbackend.application.mission

import com.example.nokbackend.application.geometry.GeometryService
import com.example.nokbackend.application.gifticon.BuyGifticonRequest
import com.example.nokbackend.application.gifticon.MemberGifticonCommandService
import com.example.nokbackend.domain.firebase.Firebase
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
class MemberMissionCommandService(
    private val missionGroupRepository: MissionGroupRepository,
    private val missionRepository: MissionRepository,
    private val memberMissionGroupRepository: MemberMissionGroupRepository,
    private val memberMissionRepository: MemberMissionRepository,
    private val geometryService: GeometryService,
    private val memberRepository: MemberRepository,
    private val resultOfMemberMissionQuestionRepository: ResultOfMemberMissionQuestionRepository,
    private val memberGifticonCommandService: MemberGifticonCommandService,
    private val firebase: Firebase,
    private val defaultDueDate: Long = 7L
) {

    fun startMission(member: Member, missionGroupId: Long): StartMissionResponse {
        val isStartedMission = memberMissionGroupRepository.existsByMissionGroupIdAndMemberId(missionGroupId, member.id)
        check(!isStartedMission) { "이미 시작한 미션입니다" }

        val missionGroup = missionGroupRepository.findByIdCheck(missionGroupId)
        val memberMissionGroup = memberMissionGroupRepository.save(
            MemberMissionGroup(
                memberId = member.id,
                missionGroupId = missionGroup.id,
                status = MemberMissionGroup.Status.ONGOING,
                dueDate = LocalDate.now().plusDays(defaultDueDate)
            )
        )

        val missions = missionRepository.findByMissionGroup(missionGroup)
        val memberMissions = missions.map {
            MemberMission(
                memberMissionGroup = memberMissionGroup,
                missionId = it.id,
                status = MemberMission.Status.ONGOING,
            )
        }
        memberMissionRepository.saveAll(memberMissions)

        return StartMissionResponse(memberMissionGroup.id)
    }

    fun completeMissionTypeOfCurrentUserLocation(
        member: Member,
        memberMissionId: Long,
        currentLocation: DistanceFromLocation
    ) {
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

        completeMemberMissionGroup(member, memberMission)
    }

    fun submitForm(formResult: FormResult) {
        val member = memberRepository.findByEmailCheck(formResult.email)
        val mission = missionRepository.findByFormIdCheck(formResult.formId)
        val memberMission =
            memberMissionRepository.findByMissionIdAndMemberMissionGroup_MemberIdCheck(mission.id, member.id)

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

        firebase.sendAppPush(
            title = "정답 제출 완료",
            body = "결과를 확인하세요",
            targetToken = member.loginInformation.fcmCode
        )

        completeMemberMissionGroup(member, memberMission)
    }


    private fun completeMemberMissionGroup(member: Member, memberMission: MemberMission) {
        val memberMissions = memberMissionRepository.findByMemberMissionGroup(memberMission.memberMissionGroup)

        if (memberMissions.any { it.status != MemberMission.Status.FINISHED }) {
            return
        }

        memberMission.memberMissionGroup.complete()

        val missionGroup = missionGroupRepository.findByIdCheck(memberMission.memberMissionGroup.missionGroupId)

        memberGifticonCommandService.registerMemberGifticon(
            member,
            BuyGifticonRequest(gifticonId = missionGroup.prizeId, quantity = 1)
        )
    }
}