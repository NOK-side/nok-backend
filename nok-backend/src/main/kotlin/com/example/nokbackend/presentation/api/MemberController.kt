package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.authentication.AuthenticationResponse
import com.example.nokbackend.application.authentication.AuthenticationService
import com.example.nokbackend.application.authentication.ConfirmAuthenticationRequest
import com.example.nokbackend.application.member.*
import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.HeaderClaim
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@RestController
@RequestMapping("/member")
class MemberController(
    private val memberService: MemberService,
    private val sessionService: SessionService,
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/register")
    fun register(
        @ApiIgnore @HeaderClaim(value = "User-Agent") userAgent: String,
        @Valid @RequestBody registerMemberRequest: RegisterMemberRequest
    ): ResponseEntity<ApiResponse<LoginResponse>> {
        val loginResponse = sessionService.generateTokenWithRegister(userAgent, registerMemberRequest)
        return responseEntity {
            body = apiResponse {
                data = loginResponse
            }
        }
    }

    @PostMapping("/login")
    fun login(
        @ApiIgnore @HeaderClaim(value = "User-Agent") userAgent: String,
        @Valid @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<ApiResponse<LoginResponse>> {
        val loginResponse = sessionService.generateTokenWithLogin(userAgent, loginRequest)
        return responseEntity {
            body = apiResponse {
                data = loginResponse
            }
        }
    }

    @PostMapping("/refresh")
    fun refreshToken(
        @ApiIgnore @HeaderClaim(value = "User-Agent") userAgent: String,
        @RequestBody refreshTokenRequest: RefreshTokenRequest
    ): ResponseEntity<ApiResponse<TokenResponse>> {
        val tokenResponse = sessionService.refreshToken(userAgent, refreshTokenRequest)
        return responseEntity {
            body = apiResponse {
                data = tokenResponse
            }
        }
    }

    @Authenticated
    @GetMapping("/me/info")
    fun findMyInfo(@ApiIgnore @MemberClaim member: Member): ResponseEntity<ApiResponse<MemberInfoResponse>> {
        return responseEntity {
            body = apiResponse {
                data = MemberInfoResponse(member)
            }
        }
    }

    @Authenticated
    @PutMapping("/me/info")
    fun updateMyInfo(
        @ApiIgnore @MemberClaim member: Member,
        @RequestBody updateMemberRequest: UpdateMemberRequest
    ): ResponseEntity<ApiResponse<UpdateMemberResponse>> {
        val updateMemberResponse = memberService.updateMemberInfo(member, updateMemberRequest)
        return responseEntity {
            body = apiResponse {
                message = "회원 정보가 수정되었습니다"
                data = updateMemberResponse
            }
        }
    }

    @Authenticated
    @PatchMapping("/me/password")
    fun updateMyPassword(
        @ApiIgnore @MemberClaim member: Member,
        @RequestBody updatePasswordRequest: UpdatePasswordRequest
    ): ResponseEntity<ApiResponse<UpdatePasswordResponse>> {
        val updatePasswordResponse = memberService.updatePassword(member, updatePasswordRequest)
        return responseEntity {
            body = apiResponse {
                data = updatePasswordResponse
            }
        }
    }

    @Authenticated
    @DeleteMapping("/me")
    fun withdraw(
        @ApiIgnore @MemberClaim member: Member,
        @RequestBody withdrawMemberRequest: WithdrawMemberRequest
    ): ResponseEntity<ApiResponse<WithdrawMemberResponse>> {
        val withResponse = memberService.withdraw(member, withdrawMemberRequest)
        return responseEntity {
            body = apiResponse {
                data = withResponse
            }
        }
    }

    @GetMapping("/find/id")
    fun findMemberEmail(findMemberIdRequest: FindMemberIdRequest): ResponseEntity<ApiResponse<FindMemberIdResponse>> {
        val findMemberIdResponse = memberService.findMemberEmail(findMemberIdRequest)
        return responseEntity {
            body = apiResponse {
                data = findMemberIdResponse
            }
        }
    }

    @PostMapping("/find/password")
    fun findMemberPassword(@RequestBody findMemberPassword: FindMemberPasswordRequest): ResponseEntity<ApiResponse<FindMemberPasswordResponse>> {
        val findMemberPasswordResponse = memberService.findMemberPassword(findMemberPassword)
        return responseEntity {
            body = apiResponse {
                data = findMemberPasswordResponse
            }
        }
    }

    @PostMapping("/send/authentication/email")
    fun sendAuthenticationToEmail(@Valid @RequestBody verifyEmailRequest: VerifyEmailRequest): ResponseEntity<ApiResponse<AuthenticationResponse>> {
        if (verifyEmailRequest.type == Authentication.Type.REGISTER) {
            memberService.checkEmailDuplication(verifyEmailRequest.email)
        }

        val authenticationResponse =
            authenticationService.sendAuthenticationToEmail(verifyEmailRequest.email, verifyEmailRequest.type)
        return responseEntity {
            body = apiResponse {
                data = authenticationResponse
            }
        }
    }

    @PostMapping("/verify/email")
    fun verifyAuthenticationCodeForEmail(@RequestBody confirmAuthenticationRequest: ConfirmAuthenticationRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        authenticationService.confirmAuthentication(confirmAuthenticationRequest, Authentication.Type.REGISTER)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }

    @PostMapping("/check/memberId/duplication")
    fun checkMemberIdDuplication(@RequestBody checkMemberIdDuplicationRequest: CheckMemberIdDuplicationRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        memberService.checkMemberIdDuplication(checkMemberIdDuplicationRequest.memberId)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }
}