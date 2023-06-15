package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.point.ChargePointRequest
import com.example.nokbackend.application.point.PointService
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/member-point")
class MemberPointController(
    private val pointService: PointService,
) {

    @Authenticated
    @PostMapping("/charge")
    fun chargePoint(@ApiIgnore @MemberClaim member: Member, @RequestBody chargePointRequest: ChargePointRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        pointService.chargePoint(member, chargePointRequest)

        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }
}