package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.cart.*
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/cart")
class CartController(
    private val cartQueryService: CartQueryService,
    private val cartCommandService: CartCommandService
) {

    @Authenticated
    @GetMapping("/me")
    fun findMyCarts(@ApiIgnore @MemberClaim member: Member): ResponseEntity<ApiResponse<List<CartResponse>>> {
        val carts = cartQueryService.findMyCart(member)

        return responseEntity {
            body = apiResponse {
                data = carts
            }
        }
    }

    @Authenticated
    @PostMapping("/register")
    fun registerItemToCart(@ApiIgnore @MemberClaim member: Member, @RequestBody registerItemToCartRequest: RegisterItemToCartRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        cartCommandService.registerItemToCart(member, registerItemToCartRequest)

        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @Authenticated
    @PatchMapping("/update/quantity")
    fun updateQuantityOfCart(@ApiIgnore @MemberClaim member: Member, @RequestBody updateQuantityOfCartRequest: ChangeQuantityOfCartRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        cartCommandService.changeQuantityOfCart(member, updateQuantityOfCartRequest)

        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @Authenticated
    @DeleteMapping("/delete/{cartId}")
    fun deleteItemFromCart(@ApiIgnore @MemberClaim member: Member, @PathVariable cartId: Long): ResponseEntity<ApiResponse<EmptyBody>> {
        cartCommandService.deleteItemFromCart(member, cartId)

        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @Authenticated
    @DeleteMapping("/flush")
    fun flushCart(@ApiIgnore @MemberClaim member: Member): ResponseEntity<ApiResponse<EmptyBody>> {
        cartCommandService.flushCart(member)

        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }
}