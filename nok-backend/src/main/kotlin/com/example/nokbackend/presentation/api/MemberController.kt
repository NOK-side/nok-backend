package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.*
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/member")
class MemberController(
    private val memberService: MemberService,
    private val sessionService: SessionService
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody registerMemberRequest: RegisterMemberRequest): ResponseEntity<Any> {
        val token = sessionService.generateTokenWithRegister(registerMemberRequest)
        return ResponseEntity.ok().body(ApiResponse.success(token))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        val token = sessionService.generateTokenWithLogin(loginRequest)
        return ResponseEntity.ok().body(ApiResponse.success(token))
    }

    @GetMapping("/me/info")
    fun findMyInfo(@MemberClaim member: Member): ResponseEntity<Any> {
        return ResponseEntity.ok().body(ApiResponse.success(MemberInfoResponse(member)))
    }

    @PutMapping("/me/info")
    fun updateMyInfo(@MemberClaim member: Member, @RequestBody updateMemberRequest: UpdateMemberRequest): ResponseEntity<Any> {
        memberService.updateMemberInfo(member, updateMemberRequest)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/me")
    fun withdraw(@MemberClaim member: Member, @RequestBody withdrawMemberRequest: WithdrawMemberRequest): ResponseEntity<Any> {
        memberService.withdraw(member, withdrawMemberRequest)
        return ResponseEntity.ok().build()
    }
}