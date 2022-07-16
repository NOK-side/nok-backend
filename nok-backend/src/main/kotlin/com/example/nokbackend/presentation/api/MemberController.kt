package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.MemberService
import com.example.nokbackend.application.RegisterMemberRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/register")
    fun register(@RequestBody registerMemberRequest: RegisterMemberRequest): ResponseEntity<Any> {
        memberService.generateTokenWithRegister(registerMemberRequest)
        return ResponseEntity.ok().build()
    }
}