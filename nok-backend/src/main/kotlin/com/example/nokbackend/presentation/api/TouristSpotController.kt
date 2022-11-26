package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tourist-spot")
class TouristSpotController(
    private val touristSpotService: TouristSpotService
) {

    @GetMapping("/by-distance")
    fun findByDistance(@RequestBody findTouristSpotByDistanceRequest: FindTouristSpotByDistanceRequest): ResponseEntity<ApiResponse<List<FindTouristSpotResponse>>> {
        val touristSpots = touristSpotService.findByDistance(findTouristSpotByDistanceRequest)
        return responseEntity {
            body = apiResponse {
                data = touristSpots
            }
        }
    }

    @GetMapping
    fun findByCondition(@RequestBody findTouristSpotCondition: FindTouristSpotCondition): ResponseEntity<ApiResponse<List<FindTouristSpotResponse>>> {
        val touristSpots = touristSpotService.findByCondition(findTouristSpotCondition)
        return responseEntity {
            body = apiResponse {
                data = touristSpots
            }
        }
    }

    @GetMapping("/info/{touristSpotId}")
    fun findTouristSpotInfo(@PathVariable touristSpotId: Long): ResponseEntity<ApiResponse<TouristSpotDetailResponse>> {
        val touristSpotDetailResponse = touristSpotService.findTouristSpotInfo(touristSpotId)
        return responseEntity {
            body = apiResponse {
                data = touristSpotDetailResponse
            }
        }
    }
}