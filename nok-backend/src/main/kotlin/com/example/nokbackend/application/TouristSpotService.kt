package com.example.nokbackend.application

import com.example.nokbackend.domain.touristspot.TouristSpotQueryRepository
import com.example.nokbackend.domain.touristspot.TouristSpotRepository
import com.example.nokbackend.domain.touristspot.findByIdCheck
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TouristSpotService(
    private val touristSpotRepository: TouristSpotRepository,
    private val touristSpotQueryRepository: TouristSpotQueryRepository
) {

    fun findWithDistance(findTouristSpotByDistanceRequest: FindTouristSpotByDistanceRequest): List<FindTouristSpotResponse> {
        val (longitude, latitude, distance) = findTouristSpotByDistanceRequest
        touristSpotRepository.findWithDistance(longitude, latitude, distance)
        return touristSpotRepository.findWithDistance(longitude, latitude, distance)
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