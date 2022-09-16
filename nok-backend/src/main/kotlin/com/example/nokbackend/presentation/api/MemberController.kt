package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.*
import com.example.nokbackend.domain.model.authentication.Authentication
import com.example.nokbackend.domain.model.member.Member
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
    fun register(@Valid @RequestBody registerMemberRequest: RegisterMemberRequest): ResponseEntity<Any> {
        val token = sessionService.generateTokenWithRegister(registerMemberRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(token))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        val token = sessionService.generateTokenWithLogin(loginRequest)
        return ResponseEntity.ok().body(ApiResponse.success(token))
    }

    @GetMapping("/me/info")
    fun findMyInfo(@ApiIgnore @MemberClaim member: Member): ResponseEntity<Any> {
        return ResponseEntity.ok().body(ApiResponse.success(MemberInfoResponse(member)))
    }

    @PutMapping("/me/info")
    fun updateMyInfo(@ApiIgnore @MemberClaim member: Member, @RequestPart updateMemberRequest: UpdateMemberRequest): ResponseEntity<Any> {
        memberService.updateMemberInfo(member, updateMemberRequest)
        return ResponseEntity.ok().body(ApiResponse.success(EmptyBody))
    }

    @PatchMapping("/me/password")
    fun updateMyPassword(@ApiIgnore @MemberClaim member: Member, @RequestBody updatePasswordRequest: UpdatePasswordRequest): ResponseEntity<Any> {
        memberService.updatePassword(member, updatePasswordRequest)
        return ResponseEntity.ok().body(ApiResponse.success(EmptyBody))
    }

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