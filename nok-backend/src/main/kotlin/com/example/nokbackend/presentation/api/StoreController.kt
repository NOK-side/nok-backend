package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.RegisterStoreRequest
import com.example.nokbackend.application.StoreService
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/store")
class StoreController(
    private val storeService: StoreService
) {

    @PostMapping("/register")
    fun registerStore(@MemberClaim member: Member, @RequestBody registerStoreRequest: RegisterStoreRequest) : ResponseEntity<Any> {
        val storeId = storeService.registerStore(member, registerStoreRequest)
        return ResponseEntity.accepted().body(storeId)
    }
}