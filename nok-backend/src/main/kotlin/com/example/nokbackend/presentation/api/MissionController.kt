package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.MissionService
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mission")
class MissionController(
    private val missionService: MissionService
) {

    @GetMapping("/mission-group/{missionGroupId}")
    fun findMissionGroupInfo(@MemberClaim member: Member, @PathVariable missionGroupId: Long): ResponseEntity<Any> {
        val missionGroupInfo = missionService.findMissionGroupInfo(member, missionGroupId)
        return ResponseEntity.ok(ApiResponse.success(missionGroupInfo))
    }

    @GetMapping("/mission-group/tourist-spot/{touristSpotId}")
    fun findMissionGroupOfTouristSpot(@MemberClaim member: Member, @PathVariable touristSpotId: Long): ResponseEntity<Any> {
        val missionGroupInfos = missionService.findMissionGroupOfTouristSpot(member, touristSpotId)
        return ResponseEntity.ok(ApiResponse.success(missionGroupInfos))
    }
}
