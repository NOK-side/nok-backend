package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.*
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member-gifticon")
class MemberGifticonController(
    private val memberGifticonService: MemberGifticonService
) {

    @Authenticated
    @GetMapping("/me")
    fun findMyGifticon(@MemberClaim member: Member): ResponseEntity<Any> {
        val myGifticons = memberGifticonService.findMyGifticon(member)
        return ResponseEntity.ok().body(ApiResponse.success(myGifticons))
    }

    @Authenticated
    @PostMapping("/buy")
    fun buyGifticon(@MemberClaim member: Member, @RequestBody buyGifticonRequest: BuyGifticonRequest): ResponseEntity<Any> {
        memberGifticonService.buyGifticon(member, buyGifticonRequest)
        return ResponseEntity.ok().build()
    }

    @Authenticated
    @PostMapping("/buy/cart")
    fun buyGifticonInCart(@MemberClaim member: Member, @RequestBody buyGifticonInCartRequest: BuyGifticonInCartRequest): ResponseEntity<Any> {
        memberGifticonService.buyGifticonInCart(member, buyGifticonInCartRequest)
        return ResponseEntity.ok().build()
    }

    @Authenticated
    @PostMapping("/send")
    fun sendGifticon(@MemberClaim member: Member, @RequestBody sendGifticonRequest: SendGifticonRequest): ResponseEntity<Any> {
        memberGifticonService.sendGifticon(member, sendGifticonRequest)
        return ResponseEntity.ok().build()
    }

    @Authenticated
    @PostMapping("/use")
    fun useGifticon(@MemberClaim member: Member, @RequestBody useGifticonRequest: UseGifticonRequest): ResponseEntity<Any> {
        memberGifticonService.useGifticon(member, useGifticonRequest)
        return ResponseEntity.ok().build()
    }
}