package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.cart.CartResponse
import com.example.nokbackend.application.cart.CartService
import com.example.nokbackend.application.cart.ChangeQuantityOfCartRequest
import com.example.nokbackend.application.cart.RegisterItemToCartRequest
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/cart")
class CartController(
    private val cartService: CartService
) {

    @Authenticated
    @GetMapping("/me")
    fun findMyCarts(@ApiIgnore @MemberClaim member: Member): ResponseEntity<ApiResponse<List<CartResponse>>> {
        val carts = cartService.findMyCart(member)

        return responseEntity {
            body = apiResponse {
                data = carts
            }
        }
    }

    @Authenticated
    @PostMapping("/register")
    fun registerItemToCart(@ApiIgnore @MemberClaim member: Member, @RequestBody registerItemToCartRequest: RegisterItemToCartRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        cartService.registerItemToCart(member, registerItemToCartRequest)

        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @Authenticated
    @PatchMapping("/update/quantity")
    fun updateQuantityOfCart(@ApiIgnore @MemberClaim member: Member, @RequestBody updateQuantityOfCartRequest: ChangeQuantityOfCartRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        cartService.changeQuantityOfCart(member, updateQuantityOfCartRequest)

        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @Authenticated
    @DeleteMapping("/delete/{cartId}")
    fun deleteItemFromCart(@ApiIgnore @MemberClaim member: Member, @PathVariable cartId: Long): ResponseEntity<ApiResponse<EmptyBody>> {
        cartService.deleteItemFromCart(member, cartId)

        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @Authenticated
    @DeleteMapping("/flush")
    fun flushCart(@ApiIgnore @MemberClaim member: Member): ResponseEntity<ApiResponse<EmptyBody>> {
        cartService.flushCart(member)

        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }
}