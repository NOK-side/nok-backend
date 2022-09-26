package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.FindStoreCondition
import com.example.nokbackend.application.RegisterStoreRequest
import com.example.nokbackend.application.StoreService
import com.example.nokbackend.application.UpdateStoreInformationRequest
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/store")
class StoreController(
    private val storeService: StoreService
) {

    @PostMapping("/register")
    fun registerStore(@RequestBody registerStoreRequest: RegisterStoreRequest): ResponseEntity<Any> {
        val storeId = storeService.registerStore(registerStoreRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(storeId)
    }

    @GetMapping
    fun findStores(findStoreCondition: FindStoreCondition): ResponseEntity<Any> {
        val stores = storeService.findByCondition(findStoreCondition)
        return ResponseEntity.ok().body(stores)
    }

    @GetMapping("/info/{storeId}")
    fun findStoreInformation(@PathVariable storeId: Long): ResponseEntity<Any> {
        val store = storeService.findStoreInformation(storeId)
        return ResponseEntity.ok().body(store)
    }

    @PutMapping("/info/{storeId}")
    fun updateStoreInformation(@ApiIgnore @MemberClaim member: Member, @PathVariable storeId: Long, @RequestBody updateStoreInformationRequest: UpdateStoreInformationRequest): ResponseEntity<Any> {
        storeService.updateStoreInformation(member, storeId, updateStoreInformationRequest)
        return ResponseEntity.ok().body(ApiResponse.success(EmptyBody))
    }
}