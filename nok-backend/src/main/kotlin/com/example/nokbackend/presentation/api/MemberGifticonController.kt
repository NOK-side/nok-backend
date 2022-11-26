package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.*
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/member-gifticon")
class MemberGifticonController(
    private val memberGifticonService: MemberGifticonService
) {

    @Authenticated
    @GetMapping("/me")
    fun findMyGifticon(@ApiIgnore @MemberClaim member: Member): ResponseEntity<ApiResponse<List<MemberGifticonResponse>>> {
        val myGifticons = memberGifticonService.findMyGifticon(member)
        return responseEntity {
            body = apiResponse {
                data = myGifticons
            }
        }
    }

    @Authenticated
    @PostMapping("/buy")
    fun buyGifticon(@ApiIgnore @MemberClaim member: Member, @RequestBody buyGifticonRequest: BuyGifticonRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        memberGifticonService.buyGifticon(member, buyGifticonRequest)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @Authenticated
    @PostMapping("/buy/cart")
    fun buyGifticonInCart(@ApiIgnore @MemberClaim member: Member, @RequestBody buyGifticonInCartRequest: BuyGifticonInCartRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        memberGifticonService.buyGifticonInCart(member, buyGifticonInCartRequest)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @Authenticated
    @PostMapping("/send")
    fun sendGifticon(@ApiIgnore @MemberClaim member: Member, @RequestBody sendGifticonRequest: SendGifticonRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        memberGifticonService.sendGifticon(member, sendGifticonRequest)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @Authenticated
    @PostMapping("/use")
    fun useGifticon(@ApiIgnore @MemberClaim member: Member, @RequestBody useGifticonRequest: UseGifticonRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        memberGifticonService.useGifticon(member, useGifticonRequest)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }
}