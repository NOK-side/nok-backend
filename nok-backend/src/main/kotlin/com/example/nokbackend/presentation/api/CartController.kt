package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.CartService
import com.example.nokbackend.application.ChangeQuantityOfCartRequest
import com.example.nokbackend.application.RegisterItemToCartRequest
import com.example.nokbackend.domain.model.member.Member
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
    fun findMyCarts(@ApiIgnore @MemberClaim member: Member): ResponseEntity<Any> {
        val carts = cartService.findMyCart(member)
        return ResponseEntity.ok().body(ApiResponse.success(carts))
    }

    @Authenticated
    @PostMapping("/register")
    fun registerItemToCart(@ApiIgnore @MemberClaim member: Member, @RequestBody registerItemToCartRequest: RegisterItemToCartRequest): ResponseEntity<Any> {
        cartService.registerItemToCart(member, registerItemToCartRequest)
        return ResponseEntity.ok().body(ApiResponse.success(EmptyBody))
    }

    @Authenticated
    @PatchMapping("/update/quantity")
    fun updateQuantityOfCart(@ApiIgnore @MemberClaim member: Member, @RequestBody updateQuantityOfCartRequest: ChangeQuantityOfCartRequest): ResponseEntity<Any> {
        cartService.changeQuantityOfCart(member, updateQuantityOfCartRequest)
        return ResponseEntity.ok().body(ApiResponse.success(EmptyBody))
    }

    @Authenticated
    @DeleteMapping("/delete/{cartId}")
    fun deleteItemFromCart(@ApiIgnore @MemberClaim member: Member, @PathVariable cartId: Long): ResponseEntity<Any> {
        cartService.deleteItemFromCart(member, cartId)
        return ResponseEntity.ok().body(ApiResponse.success(EmptyBody))
    }

    @Authenticated
    @DeleteMapping("/flush")
    fun flushCart(@ApiIgnore @MemberClaim member: Member): ResponseEntity<Any> {
        cartService.flushCart(member)
        return ResponseEntity.ok().body(ApiResponse.success(EmptyBody))
    }
}