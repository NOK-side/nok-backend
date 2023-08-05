package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.gifticon.*
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/gifticon")
class GifticonController(
    private val gifticonQueryService: GifticonQueryService,
    private val gifticonCommandService: GifticonCommandService
) {

    @Authenticated
    @PostMapping("/register")
    fun registerGifticon(@ApiIgnore @MemberClaim member: Member, @RequestBody registerGifticonRequest: RegisterGifticonRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        gifticonCommandService.registerGifticon(member, registerGifticonRequest)

        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @GetMapping("/store/{storeId}")
    fun findStoreGifticon(@PathVariable storeId: Long): ResponseEntity<ApiResponse<List<GifticonResponse>>> {
        val gifticons = gifticonQueryService.findStoreGifticon(storeId)

        return responseEntity {
            body = apiResponse {
                data = gifticons
            }
        }
    }

    @GetMapping("/info/{gifticonId}")
    fun findGifticonInfo(@PathVariable gifticonId: Long): ResponseEntity<ApiResponse<GifticonDetailResponse>> {
        val gifticon = gifticonQueryService.findGifticonInfo(gifticonId)

        return responseEntity {
            body = apiResponse {
                data = gifticon
            }
        }
    }
}