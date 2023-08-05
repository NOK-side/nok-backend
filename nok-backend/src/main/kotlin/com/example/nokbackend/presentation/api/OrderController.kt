package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.order.OrderCommandService
import com.example.nokbackend.application.order.OrderQueryService
import com.example.nokbackend.application.order.OrderRequest
import com.example.nokbackend.application.order.OrderResponse
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/order")
class OrderController(
    private val orderQueryService: OrderQueryService,
    private val orderCommandService: OrderCommandService
) {

    @Authenticated
    @PostMapping
    fun registerOrder(
        @ApiIgnore @MemberClaim member: Member,
        @RequestBody orderRequest: OrderRequest
    ): ResponseEntity<ApiResponse<EmptyBody>> {
        orderCommandService.registerOrder(member, orderRequest)

        return responseEntity {
            body = apiResponse {
                data = EmptyBody
                message = "구매에 성공하였습니다"
            }
        }
    }

    @Authenticated
    @GetMapping
    fun findMyOrder(@ApiIgnore @MemberClaim member: Member): ResponseEntity<ApiResponse<List<OrderResponse>>> {
        val orderResponses = orderQueryService.findMyOrder(member)

        return responseEntity {
            body = apiResponse {
                data = orderResponses
            }
        }
    }
}