package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.order.OrderRequest
import com.example.nokbackend.application.order.OrderResponse
import com.example.nokbackend.application.order.OrderService
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService
) {

    @Authenticated
    @PostMapping
    fun registerOrder(@ApiIgnore @MemberClaim member: Member, @RequestBody orderRequest: OrderRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        orderService.registerOrder(member, orderRequest)

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
        val orderResponses = orderService.findMyOrder(member)

        return responseEntity {
            body = apiResponse {
                data = orderResponses
            }
        }
    }
}