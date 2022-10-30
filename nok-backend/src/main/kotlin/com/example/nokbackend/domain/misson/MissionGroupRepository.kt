package com.example.nokbackend.domain.misson

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal

fun MissionGroupRepository.findByIdCheck(id: Long): MissionGroup = findByIdOrNull(id) ?: throw RuntimeException("등록된 미션그룹이 없습니다")

interface MissionGroupRepository: JpaRepository<MissionGroup, Long> {

    fun findByTouristSpotId(id: Long): List<MissionGroup>

    @Query(
        value = """
            select m
            from MissionGroup m 
            where ST_Distance_Sphere(Point(:longitude, :latitude), Point(m.location.longitude, m.location.latitude)) < :meterDistance
        """,
    )
    fun findByDistance(longitude: BigDecimal, latitude: BigDecimal, meterDistance: Int): List<MissionGroup>
}