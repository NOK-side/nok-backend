package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.*
import com.example.nokbackend.application.store.*
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.security.MemberClaim
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/store")
class StoreController(
    private val storeQueryService: StoreQueryService,
    private val storeCommandService: StoreCommandService
) {

    @PostMapping("/register")
    fun registerStore(@RequestBody registerStoreRequest: RegisterStoreRequest): ResponseEntity<ApiResponse<RegisterStoreResponse>> {
        val registerStoreResponse = storeCommandService.registerStore(registerStoreRequest)
        return responseEntity {
            body = apiResponse {
                data = registerStoreResponse
            }
        }
    }

    @GetMapping
    fun findStores(findStoreCondition: FindStoreCondition): ResponseEntity<ApiResponse<List<StoreResponse>>> {
        val stores = storeQueryService.findByCondition(findStoreCondition)
        return responseEntity {
            body = apiResponse {
                data = stores
            }
        }
    }

    @GetMapping("/info/{storeId}")
    fun findStoreInformation(@PathVariable storeId: Long): ResponseEntity<ApiResponse<StoreDetailResponse>> {
        val store = storeQueryService.findStoreInformation(storeId)
        return responseEntity {
            body = apiResponse {
                data = store
            }
        }
    }

    @PutMapping("/info/{storeId}")
    fun updateStoreInformation(
        @ApiIgnore @MemberClaim member: Member,
        @PathVariable storeId: Long,
        @RequestBody updateStoreInformationRequest: UpdateStoreInformationRequest
    ): ResponseEntity<ApiResponse<EmptyBody>> {
        storeCommandService.updateStoreInformation(member, storeId, updateStoreInformationRequest)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }
}