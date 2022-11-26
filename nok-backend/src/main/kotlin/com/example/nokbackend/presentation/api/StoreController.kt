package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.*
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/store")
class StoreController(
    private val storeService: StoreService
) {

    @PostMapping("/register")
    fun registerStore(@RequestBody registerStoreRequest: RegisterStoreRequest): ResponseEntity<ApiResponse<RegisterStoreResponse>> {
        val registerStoreResponse = storeService.registerStore(registerStoreRequest)
        return responseEntity {
            body = apiResponse {
                data = registerStoreResponse
            }
        }
    }

    @GetMapping
    fun findStores(findStoreCondition: FindStoreCondition): ResponseEntity<ApiResponse<List<StoreResponse>>> {
        val stores = storeService.findByCondition(findStoreCondition)
        return responseEntity {
            body = apiResponse {
                data = stores
            }
        }
    }

    @GetMapping("/info/{storeId}")
    fun findStoreInformation(@PathVariable storeId: Long): ResponseEntity<ApiResponse<StoreDetailResponse>> {
        val store = storeService.findStoreInformation(storeId)
        return responseEntity {
            body = apiResponse {
                data = store
            }
        }
    }

    @PutMapping("/info/{storeId}")
    fun updateStoreInformation(@ApiIgnore @MemberClaim member: Member, @PathVariable storeId: Long, @RequestBody updateStoreInformationRequest: UpdateStoreInformationRequest): ResponseEntity<ApiResponse<EmptyBody>> {
        storeService.updateStoreInformation(member, storeId, updateStoreInformationRequest)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }
}