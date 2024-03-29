package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.gifticon.*
import com.example.nokbackend.application.util.CodeService
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.membergifticon.MemberGifticon
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/member-gifticon")
class MemberGifticonController(
    private val memberGifticonCommandService: MemberGifticonCommandService,
    private val memberGifticonQueryService: MemberGifticonQueryService,
    private val codeService: CodeService
) {

    @Authenticated
    @GetMapping("/me")
    fun findMyGifticon(@ApiIgnore @MemberClaim member: Member): ResponseEntity<ApiResponse<List<MemberGifticonResponse>>> {
        val myGifticons = memberGifticonQueryService.findMyGifticon(member, MemberGifticon.Status.ACTIVE)
        return responseEntity {
            body = apiResponse {
                data = myGifticons
            }
        }
    }

    @Authenticated
    @GetMapping("/me/used")
    fun findMyUsedGifticon(@ApiIgnore @MemberClaim member: Member): ResponseEntity<ApiResponse<List<MemberGifticonResponse>>> {
        val myGifticons = memberGifticonQueryService.findMyGifticon(member, MemberGifticon.Status.USED)
        return responseEntity {
            body = apiResponse {
                data = myGifticons
            }
        }
    }

    @Authenticated
    @PostMapping("/buy")
    fun buyGifticon(
        @ApiIgnore @MemberClaim member: Member,
        @RequestBody buyGifticonRequest: BuyGifticonRequest
    ): ResponseEntity<ApiResponse<EmptyBody>> {
        memberGifticonCommandService.registerMemberGifticon(member, buyGifticonRequest)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @Authenticated
    @PostMapping("/send")
    fun sendGifticon(
        @ApiIgnore @MemberClaim member: Member,
        @RequestBody sendGifticonRequest: SendGifticonRequest
    ): ResponseEntity<ApiResponse<EmptyBody>> {
        memberGifticonCommandService.sendGifticon(member, sendGifticonRequest)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @Authenticated
    @PostMapping("/use")
    fun useGifticon(
        @ApiIgnore @MemberClaim member: Member,
        @RequestBody useGifticonRequest: UseGifticonRequest
    ): ResponseEntity<ApiResponse<EmptyBody>> {
        memberGifticonCommandService.useGifticon(member, useGifticonRequest)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @Authenticated
    @GetMapping("/info/qr/{memberGifticonId}")
    fun test(@ApiIgnore @MemberClaim member: Member, @PathVariable memberGifticonId: Long): ResponseEntity<ByteArray> {
        val memberGifticonInfo = memberGifticonQueryService.findMemberGifticonInfo(member, memberGifticonId)
        val bytes = codeService.createCodeImage(memberGifticonInfo.orderId, CodeService.CodeType.BARCODE)

        return responseEntity {
            contentType = MediaType.IMAGE_PNG
            body = bytes
        }
    }
}