package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.*
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/mission")
class MissionController(
    private val missionService: MissionService
) {

    @GetMapping("/mission-group/{missionGroupId}")
    fun findMissionGroupInfo(@ApiIgnore @MemberClaim member: Member, @PathVariable missionGroupId: Long): ResponseEntity<ApiResponse<MissionGroupInfoResponse>> {
        val missionGroupInfo = missionService.findMissionGroupInfo(member, missionGroupId)
        return responseEntity {
            body = apiResponse {
                data = missionGroupInfo
            }
        }
    }

    @GetMapping("/mission-group/tourist-spot/{touristSpotId}")
    fun findMissionGroupOfTouristSpot(@ApiIgnore @MemberClaim member: Member, @PathVariable touristSpotId: Long): ResponseEntity<ApiResponse<List<MissionGroupInfoResponse>>> {
        val missionGroupInfos = missionService.findMissionGroupOfTouristSpot(member, touristSpotId)
        return responseEntity {
            body = apiResponse {
                data = missionGroupInfos
            }
        }
    }

    @GetMapping("/mission-group")
    fun findMissionGroupByCondition(@ApiIgnore @MemberClaim member: Member, findMissionGroupCondition: FindMissionGroupCondition): ResponseEntity<ApiResponse<List<MissionGroupInfoResponse>>> {
        val missionGroupInfos = missionService.findMissionGroupByCondition(member, findMissionGroupCondition)
        return responseEntity {
            body = apiResponse {
                data = missionGroupInfos
            }
        }
    }

    @GetMapping("/me/mission-group")
    fun findMyMission(@ApiIgnore @MemberClaim member: Member): ResponseEntity<ApiResponse<List<MissionGroupInfoResponse>>> {
        val missionGroupInfos = missionService.findMyMission(member)
        return responseEntity {
            body = apiResponse {
                data = missionGroupInfos
            }
        }
    }

    @GetMapping("/cities")
    fun findCitiesOfMission(findCitiesRequest: FindCitiesRequest): ResponseEntity<ApiResponse<List<FindCitiesResponse>>> {
        val locations = missionService.findCitiesOfMission(findCitiesRequest)
        return responseEntity {
            body = apiResponse {
                data = locations
            }
        }
    }

    @PostMapping("/start/mission-group/{missionGroupId}")
    fun startMission(@ApiIgnore @MemberClaim member: Member, @PathVariable missionGroupId: Long): ResponseEntity<ApiResponse<EmptyBody>> {
        missionService.startMission(member, missionGroupId)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @PostMapping("/complete/current-user-location/{memberMissionId}")
    fun completeMissionTypeOfCurrentUserLocation(@ApiIgnore @MemberClaim member: Member, @PathVariable memberMissionId: Long, @RequestBody currentLocation: DistanceFromLocation): ResponseEntity<ApiResponse<EmptyBody>> {
        missionService.completeMissionTypeOfCurrentUserLocation(member, memberMissionId, currentLocation)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @PostMapping("/submit/answer")
    fun submitFromResult(@RequestBody formResult: FromResult): ResponseEntity<ApiResponse<EmptyBody>> {
        missionService.submitFromResult(formResult)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @GetMapping("/qr/question-form/{missionId}")
    fun getQRCodeFromQuestion(@ApiIgnore @MemberClaim member: Member, @PathVariable missionId: Long): ResponseEntity<ByteArray> {
        val qrCodeByteArray = missionService.getQRCodeFromQuestion(member, missionId)
        return responseEntity {
            contentType = MediaType.IMAGE_PNG
            body = qrCodeByteArray
        }
    }
}
