package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.order.OrderRequest
import com.example.nokbackend.application.order.OrderService
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
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService
) {

    @Authenticated
    @PostMapping
    fun registerOrder(@ApiIgnore  @MemberClaim member: Member, @RequestBody orderRequest: OrderRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        orderService.registerOrder(member, orderRequest)

        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }
}