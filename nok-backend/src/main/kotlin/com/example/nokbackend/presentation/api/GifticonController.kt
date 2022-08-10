package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.FindGifticonCondition
import com.example.nokbackend.application.GifticonService
import com.example.nokbackend.application.RegisterGifticonRequest
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/gifticon")
class GifticonController(
    private val gifticonService: GifticonService
) {

    @PostMapping("/register")
    fun registerGifticon(@MemberClaim member: Member, @RequestBody registerGifticonRequest: RegisterGifticonRequest): ResponseEntity<Any> {
        gifticonService.registerGifticon(member, registerGifticonRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping
    fun findGifticon(@RequestBody findGifticonCondition: FindGifticonCondition): ResponseEntity<Any> {
        val gifticons = gifticonService.findByCondition(findGifticonCondition)
        return ResponseEntity.ok().body(gifticons)
    }

    @GetMapping("/info/{gifticonId}")
    fun findGifticonInfo(@PathVariable gifticonId: Long): ResponseEntity<Any> {
        val gifticon = gifticonService.findGifticonInfo(gifticonId)
        return ResponseEntity.ok().body(gifticon)
    }
}