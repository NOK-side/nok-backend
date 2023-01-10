package com.example.nokbackend.domain.misson

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal

fun MissionGroupRepository.findByIdCheck(id: Long): MissionGroup = findByIdOrNull(id) ?: throw RuntimeException("등록된 미션그룹이 없습니다")

fun MissionGroupRepository.findByCityName(cityName: String): List<MissionGroup> = findByLocationLandNumberAddressContainingOrLocationRoadNameAddressContaining(cityName, cityName)

interface MissionGroupRepository : JpaRepository<MissionGroup, Long> {

    fun findByLocationLandNumberAddressContainingOrLocationRoadNameAddressContaining(landNumberAddress: String, roadNameAddress: String): List<MissionGroup>

    @Query(
        value = """
            select m.id
            from MissionGroup m 
            where ST_Distance_Sphere(Point(:longitude, :latitude), Point(m.location.longitude, m.location.latitude)) < :meterDistance
        """,
    )
    fun findIdByDistance(longitude: BigDecimal, latitude: BigDecimal, meterDistance: Int): List<Long>
}