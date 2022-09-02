package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.FindTouristSpotByDistanceRequest
import com.example.nokbackend.application.FindTouristSpotCondition
import com.example.nokbackend.application.TouristSpotService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tourist-spot")
class TouristSpotController(
    private val touristSpotService: TouristSpotService
) {

    @GetMapping("/by-distance")
    fun findWithDistance(@RequestBody findTouristSpotByDistanceRequest: FindTouristSpotByDistanceRequest): ResponseEntity<Any> {
        val touristSpots = touristSpotService.findWithDistance(findTouristSpotByDistanceRequest)
        return ResponseEntity.ok().body(ApiResponse.success(touristSpots))
    }

    @GetMapping
    fun findByCondition(@RequestBody findTouristSpotCondition: FindTouristSpotCondition): ResponseEntity<Any> {
        val touristSpots = touristSpotService.findByCondition(findTouristSpotCondition)
        return ResponseEntity.ok().body(ApiResponse.success(touristSpots))
    }

    @GetMapping("/info/{touristSpotId}")
    fun findTouristSpotInfo(@PathVariable touristSpotId: Long): ResponseEntity<Any> {
        val touristSpotDetailResponse = touristSpotService.findTouristSpotInfo(touristSpotId)
        return ResponseEntity.ok().body(ApiResponse.success(touristSpotDetailResponse))
    }
}