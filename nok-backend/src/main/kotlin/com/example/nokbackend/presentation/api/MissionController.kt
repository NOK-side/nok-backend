package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.DistanceFromLocation
import com.example.nokbackend.application.MissionService
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/mission")
class MissionController(
    private val missionService: MissionService
) {

    @GetMapping("/mission-group/{missionGroupId}")
    fun findMissionGroupInfo(@ApiIgnore @MemberClaim member: Member, @PathVariable missionGroupId: Long): ResponseEntity<Any> {
        val missionGroupInfo = missionService.findMissionGroupInfo(member, missionGroupId)
        return ResponseEntity.ok(ApiResponse.success(missionGroupInfo))
    }

    @GetMapping("/mission-group/tourist-spot/{touristSpotId}")
    fun findMissionGroupOfTouristSpot(@ApiIgnore @MemberClaim member: Member, @PathVariable touristSpotId: Long): ResponseEntity<Any> {
        val missionGroupInfos = missionService.findMissionGroupOfTouristSpot(member, touristSpotId)
        return ResponseEntity.ok(ApiResponse.success(missionGroupInfos))
    }

    @GetMapping("/mission-group/by-distance")
    fun findMissionGroupByDistance(@ApiIgnore @MemberClaim member: Member, distanceFromLocation: DistanceFromLocation): ResponseEntity<Any> {
        val missionGroupInfos = missionService.findMissionGroupByDistance(member, distanceFromLocation)
        return ResponseEntity.ok(ApiResponse.success(missionGroupInfos))
    }

    @GetMapping("/me/mission-group")
    fun findMyMission(@ApiIgnore @MemberClaim member: Member): ResponseEntity<Any> {
        val missionGroupInfos = missionService.findMyMission(member)
        return ResponseEntity.ok(ApiResponse.success(missionGroupInfos))
    }

    @PostMapping("/start/mission-group/{missionGroupId}")
    fun startMission(@ApiIgnore @MemberClaim member: Member, @PathVariable missionGroupId: Long): ResponseEntity<Any> {
        missionService.startMission(member, missionGroupId)
        return ResponseEntity.ok(ApiResponse.success(EmptyBody))
    }

    @PostMapping("/complete/current-user-location/{memberMissionId}")
    fun completeMissionTypeOfCurrentUserLocation(@ApiIgnore @MemberClaim member: Member, @PathVariable memberMissionId: Long, @RequestBody currentLocation: DistanceFromLocation): ResponseEntity<Any> {
        missionService.completeMissionTypeOfCurrentUserLocation(member, memberMissionId, currentLocation)
        return ResponseEntity.ok(ApiResponse.success(EmptyBody))
    }
}
