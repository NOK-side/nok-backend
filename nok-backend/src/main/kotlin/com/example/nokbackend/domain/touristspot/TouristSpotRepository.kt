package com.example.nokbackend.domain.touristspot

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal

fun TouristSpotRepository.findByIdCheck(id: Long): TouristSpot = findByIdOrNull(id) ?: throw RuntimeException("관광지정보가 존재하지 않습니다")

interface TouristSpotRepository : JpaRepository<TouristSpot, Long> {

    @Query(
        value = """
            select t
            from TouristSpot t 
            where ST_Distance_Sphere(Point(:longitude, :latitude), Point(t.location.longitude, t.location.latitude)) < :meterDistance
        """,
    )
    fun findByDistance(longitude: BigDecimal, latitude: BigDecimal, meterDistance: Int): List<TouristSpot>
}