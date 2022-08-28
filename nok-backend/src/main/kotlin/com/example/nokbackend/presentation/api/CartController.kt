package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.CartService
import com.example.nokbackend.application.ChangeQuantityOfCartRequest
import com.example.nokbackend.application.RegisterItemToCartRequest
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
class CartController(
    private val cartService: CartService
) {

    @Authenticated
    @GetMapping("/me")
    fun findMyCarts(@MemberClaim member: Member): ResponseEntity<Any> {
        val carts = cartService.findMyCart(member)
        return ResponseEntity.ok().body(ApiResponse.success(carts))
    }

    @Authenticated
    @PostMapping("/register")
    fun registerItemToCart(@MemberClaim member: Member, @RequestBody registerItemToCartRequest: RegisterItemToCartRequest): ResponseEntity<Any> {
        cartService.registerItemToCart(member, registerItemToCartRequest)
        return ResponseEntity.accepted().build()
    }

    @Authenticated
    @PatchMapping("/update/quantity")
    fun updateQuantityOfCart(@MemberClaim member: Member, @RequestBody updateQuantityOfCartRequest: ChangeQuantityOfCartRequest): ResponseEntity<Any> {
        cartService.changeQuantityOfCart(member, updateQuantityOfCartRequest)
        return ResponseEntity.accepted().build()
    }

    @Authenticated
    @DeleteMapping("/delete/{cartId}")
    fun deleteItemFromCart(@MemberClaim member: Member, @PathVariable cartId: Long): ResponseEntity<Any> {
        cartService.deleteItemFromCart(member, cartId)
        return ResponseEntity.accepted().build()
    }

    @Authenticated
    @DeleteMapping("/flush")
    fun flushCart(@MemberClaim member: Member): ResponseEntity<Any> {
        cartService.flushCart(member)
        return ResponseEntity.accepted().build()
    }
}