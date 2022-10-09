package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.*
import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.Authenticated
import com.example.nokbackend.security.HeaderClaim
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.HttpStatus
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
    fun register(@ApiIgnore @HeaderClaim(value = "User-Agent") userAgent: String,@Valid @RequestBody registerMemberRequest: RegisterMemberRequest): ResponseEntity<Any> {
        val token = sessionService.generateTokenWithRegister(userAgent, registerMemberRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(token))
    }

    @PostMapping("/login")
    fun login(@ApiIgnore @HeaderClaim(value = "User-Agent") userAgent: String,@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        val token = sessionService.generateTokenWithLogin(userAgent, loginRequest)
        return ResponseEntity.ok().body(ApiResponse.success(token))
    }

    @PostMapping("/refresh")
    fun refreshToken(@ApiIgnore @HeaderClaim(value = "User-Agent") userAgent: String, @RequestBody refreshTokenRequest: RefreshTokenRequest): ResponseEntity<Any> {
        val tokenResponse = sessionService.refreshToken(userAgent, refreshTokenRequest)
        return ResponseEntity.ok().body(ApiResponse.success(tokenResponse))
    }

    @Authenticated
    @GetMapping("/me/info")
    fun findMyInfo(@ApiIgnore @MemberClaim member: Member): ResponseEntity<Any> {
        return ResponseEntity.ok().body(ApiResponse.success(MemberInfoResponse(member)))
    }

    @Authenticated
    @PutMapping("/me/info")
    fun updateMyInfo(@ApiIgnore @MemberClaim member: Member, @RequestBody updateMemberRequest: UpdateMemberRequest): ResponseEntity<Any> {
        val updateMemberResponse = memberService.updateMemberInfo(member, updateMemberRequest)
        return ResponseEntity.ok().body(ApiResponse.success(updateMemberResponse) { "회원 정보가 수정되었습니다" })
    }

    @Authenticated
    @PatchMapping("/me/password")
    fun updateMyPassword(@ApiIgnore @MemberClaim member: Member, @RequestBody updatePasswordRequest: UpdatePasswordRequest): ResponseEntity<Any> {
        val updatePasswordResponse = memberService.updatePassword(member, updatePasswordRequest)
        return ResponseEntity.ok().body(ApiResponse.success(updatePasswordResponse))
    }

    @Authenticated
    @DeleteMapping("/me")
    fun withdraw(@ApiIgnore @MemberClaim member: Member, @RequestBody withdrawMemberRequest: WithdrawMemberRequest): ResponseEntity<Any> {
        memberService.withdraw(member, withdrawMemberRequest)
        return ResponseEntity.ok().body(ApiResponse.success(EmptyBody))
    }

    @GetMapping("/find/id")
    fun findMemberEmail(@RequestBody findMemberIdRequest: FindMemberIdRequest): ResponseEntity<Any> {
        val findMemberIdResponse = memberService.findMemberEmail(findMemberIdRequest)
        return ResponseEntity.ok().body(ApiResponse.success(findMemberIdResponse))
    }

    @PostMapping("/find/password")
    fun findMemberPassword(@RequestBody findMemberPassword: FindMemberPasswordRequest): ResponseEntity<Any> {
        val findMemberPasswordResponse = memberService.findMemberPassword(findMemberPassword)
        return ResponseEntity.ok().body(ApiResponse.success(findMemberPasswordResponse))
    }

    @PostMapping("/send/authentication/email")
    fun sendAuthenticationToEmail(@Valid @RequestBody verifyEmailRequest: VerifyEmailRequest): ResponseEntity<Any> {
        if (verifyEmailRequest.type == Authentication.Type.REGISTER) {
            memberService.checkEmailDuplication(verifyEmailRequest.email)
        }

        val authenticationResponse = authenticationService.sendAuthenticationToEmail(verifyEmailRequest.email, verifyEmailRequest.type)
        return ResponseEntity.ok().body(ApiResponse.success(authenticationResponse))
    }

    @PostMapping("/verify/email")
    fun verifyAuthenticationCodeForEmail(@RequestBody confirmAuthenticationRequest: ConfirmAuthenticationRequest): ResponseEntity<Any> {
        authenticationService.confirmAuthentication(confirmAuthenticationRequest, Authentication.Type.REGISTER)
        return ResponseEntity.ok().body(ApiResponse.success(EmptyBody))
    }

    @PostMapping("/check/memberId/duplication")
    fun checkMemberIdDuplication(@RequestBody checkMemberIdDuplicationRequest: CheckMemberIdDuplicationRequest): ResponseEntity<Any> {
        memberService.checkMemberIdDuplication(checkMemberIdDuplicationRequest.memberId)
        return ResponseEntity.ok().body(ApiResponse.success(EmptyBody))
    }
}