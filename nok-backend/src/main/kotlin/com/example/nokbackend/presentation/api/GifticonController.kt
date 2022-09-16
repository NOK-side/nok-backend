package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.GifticonService
import com.example.nokbackend.application.RegisterGifticonRequest
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/gifticon")
class GifticonController(
    private val gifticonService: GifticonService
) {

    @Authenticated
    @PostMapping("/register")
    fun registerGifticon(@ApiIgnore @MemberClaim member: Member, @RequestBody registerGifticonRequest: RegisterGifticonRequest): ResponseEntity<Any> {
        gifticonService.registerGifticon(member, registerGifticonRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/store/{storeId}")
    fun findStoreGifticon(@PathVariable storeId: Long): ResponseEntity<Any> {
        val gifticons = gifticonService.findStoreGifticon(storeId)
        return ResponseEntity.ok().body(ApiResponse.success(gifticons))
    }

    @GetMapping("/info/{gifticonId}")
    fun findGifticonInfo(@PathVariable gifticonId: Long): ResponseEntity<Any> {
        val gifticon = gifticonService.findGifticonInfo(gifticonId)
        return ResponseEntity.ok().body(ApiResponse.success(gifticon))
    }
}