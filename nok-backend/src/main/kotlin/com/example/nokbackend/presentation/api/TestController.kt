package com.example.nokbackend.presentation.api

import com.example.nokbackend.domain.memberGifticon.MemberGifticonRepository
import com.example.nokbackend.domain.membermission.MemberMissionRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(
    private val memberMissionRepository: MemberMissionRepository,
    private val memberGifticonRepository: MemberGifticonRepository
) {

    @DeleteMapping("/member-mission")
    fun deleteMemberMission(): ResponseEntity<Any> {
        memberMissionRepository.deleteAll()
        return ResponseEntity.ok().body(ApiResponse.success(EmptyBody))
    }

    @DeleteMapping("/member-gifticon")
    fun deleteMemberGifticon(): ResponseEntity<Any> {
        memberGifticonRepository.deleteAll()
        return ResponseEntity.ok().body(ApiResponse.success(EmptyBody))
    }
}