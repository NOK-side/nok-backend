package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.*
import com.example.nokbackend.application.mission.*
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/mission")
class MissionController(
    private val missionQueryService: MissionQueryService
) {

    @GetMapping("/mission-group/{missionGroupId}")
    fun findMissionGroupInfo(
        @ApiIgnore @MemberClaim member: Member,
        @PathVariable missionGroupId: Long
    ): ResponseEntity<ApiResponse<MissionGroupInfoResponse>> {
        val missionGroupInfo = missionQueryService.findMissionGroupInfo(member, missionGroupId)
        return responseEntity {
            body = apiResponse {
                data = missionGroupInfo
            }
        }
    }

    @GetMapping("/mission-group/tourist-spot/{touristSpotId}")
    fun findMissionGroupOfTouristSpot(
        @ApiIgnore @MemberClaim member: Member,
        @PathVariable touristSpotId: Long
    ): ResponseEntity<ApiResponse<List<MissionGroupInfoResponse>>> {
        val missionGroupInfos = missionQueryService.findMissionGroupOfTouristSpot(member, touristSpotId)
        return responseEntity {
            body = apiResponse {
                data = missionGroupInfos
            }
        }
    }

    @GetMapping("/mission-group")
    fun findMissionGroupByCondition(
        @ApiIgnore @MemberClaim member: Member,
        findMissionGroupCondition: FindMissionGroupCondition
    ): ResponseEntity<ApiResponse<List<MissionGroupInfoResponse>>> {
        val missionGroupInfos = missionQueryService.findMissionGroupByCondition(member, findMissionGroupCondition)
        return responseEntity {
            body = apiResponse {
                data = missionGroupInfos
            }
        }
    }

    @GetMapping("/cities")
    fun findCitiesOfMission(findCitiesRequest: FindCitiesRequest): ResponseEntity<ApiResponse<List<FindCitiesResponse>>> {
        val locations = missionQueryService.findCitiesOfMission(findCitiesRequest)
        return responseEntity {
            body = apiResponse {
                data = locations
            }
        }
    }

    @GetMapping("/qr/question-form/{missionId}")
    fun getQRCodeFromQuestion(
        @ApiIgnore @MemberClaim member: Member,
        @PathVariable missionId: Long
    ): ResponseEntity<ByteArray> {
        val qrCodeByteArray = missionQueryService.getQRCodeFromQuestion(member, missionId)
        return responseEntity {
            contentType = MediaType.IMAGE_PNG
            body = qrCodeByteArray
        }
    }
}
