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
    fun deleteMemberMission(): ResponseEntity<ApiResponse<EmptyBody>> {
        memberMissionRepository.deleteAll()
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @DeleteMapping("/member-gifticon")
    fun deleteMemberGifticon(): ResponseEntity<ApiResponse<EmptyBody>> {
        memberGifticonRepository.deleteAll()
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }
}