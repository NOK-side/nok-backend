package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.mission.*
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.membermission.MemberMissionGroup
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/member-mission")
class MemberMissionController(
    private val memberMissionQueryService: MemberMissionQueryService,
    private val memberMissionCommandService: MemberMissionCommandService
) {

    @Authenticated
    @GetMapping("/me/mission-group")
    fun findMyMission(@ApiIgnore @MemberClaim member: Member): ResponseEntity<ApiResponse<List<MissionGroupInfoResponse>>> {
        val missionGroupInfos = memberMissionQueryService.findMyMission(member, MemberMissionGroup.Status.ONGOING)
        return responseEntity {
            body = apiResponse {
                data = missionGroupInfos
            }
        }
    }


    @Authenticated
    @GetMapping("/me/mission-group/completed")
    fun findMyCompletedMission(@ApiIgnore @MemberClaim member: Member): ResponseEntity<ApiResponse<List<MissionGroupInfoResponse>>> {
        val missionGroupInfos = memberMissionQueryService.findMyMission(member, MemberMissionGroup.Status.FINISHED)
        return responseEntity {
            body = apiResponse {
                data = missionGroupInfos
            }
        }
    }

    @Authenticated
    @GetMapping("/me/mission-group/{memberMissionGroupId}")
    fun findMyMissionByMemberMissionGroupId(
        @ApiIgnore @MemberClaim member: Member,
        @PathVariable memberMissionGroupId: Long
    ): ResponseEntity<ApiResponse<MissionGroupInfoResponse>> {
        val missionGroupInfo =
            memberMissionQueryService.findMyMissionByMemberMissionGroupId(member, memberMissionGroupId)
        return responseEntity {
            body = apiResponse {
                data = missionGroupInfo
            }
        }
    }


    @Authenticated
    @PostMapping("/complete/current-user-location/{memberMissionId}")
    fun completeMissionTypeOfCurrentUserLocation(
        @ApiIgnore @MemberClaim member: Member,
        @PathVariable memberMissionId: Long,
        @RequestBody currentLocation: DistanceFromLocation
    ): ResponseEntity<ApiResponse<EmptyBody>> {
        memberMissionCommandService.completeMissionTypeOfCurrentUserLocation(member, memberMissionId, currentLocation)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @PostMapping("/submit/answer")
    fun submitFromResult(@RequestBody formResult: FormResult): ResponseEntity<ApiResponse<EmptyBody>> {
        memberMissionCommandService.submitForm(formResult)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @Authenticated
    @PostMapping("/start/mission-group/{missionGroupId}")
    fun startMission(
        @ApiIgnore @MemberClaim member: Member,
        @PathVariable missionGroupId: Long
    ): ResponseEntity<ApiResponse<StartMissionResponse>> {
        val startMissionResponse = memberMissionCommandService.startMission(member, missionGroupId)
        return responseEntity {
            body = apiResponse {
                data = startMissionResponse
                message = "미션이 시작되었습니다"
            }
        }
    }
}