package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.point.*
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/member-point")
class MemberPointController(
    private val pointQueryService: PointQueryService,
    private val pointCommandService: PointCommandService
) {

    @Authenticated
    @PostMapping("/charge")
    fun chargePoint(
        @ApiIgnore @MemberClaim member: Member,
        @RequestBody chargePointRequest: ChargePointRequest
    ): ResponseEntity<ApiResponse<EmptyBody>> {
        pointCommandService.chargePoint(member, chargePointRequest)

        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @Authenticated
    @GetMapping("/me")
    fun findMyPoint(@ApiIgnore @MemberClaim member: Member): ResponseEntity<ApiResponse<MemberPointResponse>> {
        val point = pointQueryService.findMyPoint(member)

        return responseEntity {
            body = apiResponse {
                data = point
            }
        }
    }

    @Authenticated
    @GetMapping("/me/charge")
    fun findMyPointCharge(@ApiIgnore @MemberClaim member: Member): ResponseEntity<ApiResponse<List<MemberPointChargeResponse>>> {
        val memberPointChargeResponses = pointQueryService.findMyPointCharge(member)

        return responseEntity {
            body = apiResponse {
                data = memberPointChargeResponses
            }
        }
    }
}