package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.*
import com.example.nokbackend.application.touristspot.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tourist-spot")
class TouristSpotController(
    private val touristSpotQueryService: TouristSpotQueryService
) {

    @GetMapping("/by-distance")
    fun findByDistance(@RequestBody findTouristSpotByDistanceRequest: FindTouristSpotByDistanceRequest): ResponseEntity<ApiResponse<List<FindTouristSpotResponse>>> {
        val touristSpots = touristSpotQueryService.findByDistance(findTouristSpotByDistanceRequest)
        return responseEntity {
            body = apiResponse {
                data = touristSpots
            }
        }
    }

    @GetMapping
    fun findByCondition(@RequestBody findTouristSpotCondition: FindTouristSpotCondition): ResponseEntity<ApiResponse<List<FindTouristSpotResponse>>> {
        val touristSpots = touristSpotQueryService.findByCondition(findTouristSpotCondition)
        return responseEntity {
            body = apiResponse {
                data = touristSpots
            }
        }
    }

    @GetMapping("/info/{touristSpotId}")
    fun findTouristSpotInfo(@PathVariable touristSpotId: Long): ResponseEntity<ApiResponse<TouristSpotDetailResponse>> {
        val touristSpotDetailResponse = touristSpotQueryService.findTouristSpotInfo(touristSpotId)
        return responseEntity {
            body = apiResponse {
                data = touristSpotDetailResponse
            }
        }
    }
}