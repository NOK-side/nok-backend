package com.example.nokbackend.application.touristspot

import com.example.nokbackend.domain.touristspot.TouristSpotQueryRepository
import com.example.nokbackend.domain.touristspot.TouristSpotRepository
import com.example.nokbackend.domain.touristspot.findByIdCheck
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TouristSpotQueryService(
    private val touristSpotRepository: TouristSpotRepository,
    private val touristSpotQueryRepository: TouristSpotQueryRepository
) {

    fun findByDistance(findTouristSpotByDistanceRequest: FindTouristSpotByDistanceRequest): List<FindTouristSpotResponse> {
        val (longitude, latitude, distance) = findTouristSpotByDistanceRequest
        return touristSpotRepository.findByDistance(longitude, latitude, distance)
            .map { FindTouristSpotResponse(it) }
    }

    fun findByCondition(findTouristSpotCondition: FindTouristSpotCondition): List<FindTouristSpotResponse> {
        return touristSpotQueryRepository.findByCondition(findTouristSpotCondition)
            .map { FindTouristSpotResponse(it) }
    }

    fun findTouristSpotInfo(id: Long): TouristSpotDetailResponse {
        val touristSpot = touristSpotRepository.findByIdCheck(id)
        return TouristSpotDetailResponse(touristSpot)
    }
}