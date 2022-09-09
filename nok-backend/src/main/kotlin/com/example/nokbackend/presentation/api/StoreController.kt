package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.*
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    fun updateStoreInformation(@MemberClaim member: Member, @PathVariable storeId: Long, @RequestBody updateStoreInformationRequest: UpdateStoreInformationRequest): ResponseEntity<Any> {
        storeService.updateStoreInformation(member, storeId, updateStoreInformationRequest)
        return ResponseEntity.ok().body(ApiResponse.success(null))
    }

    @PutMapping("/info/{storeId}/menu")
    fun updateStoreMenus(@MemberClaim member: Member, @PathVariable storeId: Long, @RequestBody updateMenuRequest: List<UpdateMenuRequest>): ResponseEntity<Any> {
        storeService.updateMenu(member, storeId, updateMenuRequest)
        return ResponseEntity.ok().body(ApiResponse.success(null))
    }

    @DeleteMapping("/info/{storeId}/menu")
    fun deleteStoreMenus(@MemberClaim member: Member, @PathVariable storeId: Long, @RequestBody deleteMenuRequest: List<DeleteMenuRequest>): ResponseEntity<Any> {
        storeService.deleteMenu(member, storeId, deleteMenuRequest)
        return ResponseEntity.ok().body(ApiResponse.success(null))
    }
}