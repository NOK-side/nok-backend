package com.example.nokbackend.domain.misson

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.findByIdOrNull
import java.math.BigDecimal

fun MissionRepository.findByIdCheck(id: Long): Mission = findByIdOrNull(id) ?: throw RuntimeException("미션이 존재하지 않습니다")
fun MissionRepository.findByFormIdCheck(formId: String): Mission = findByFormId(formId) ?: throw RuntimeException("미션이 존재하지 않습니다")

interface MissionRepository: JpaRepository<Mission, Long> {

    fun findByMissionGroup(missionGroup: MissionGroup): List<Mission>

    fun findByMissionGroupIn(missionGroups: List<MissionGroup>): List<Mission>

    fun findByFormId(formId: String): Mission?

    @Query(
        value = """
            select m
            from Mission m 
            where ST_Distance_Sphere(Point(:longitude, :latitude), Point(m.location.longitude, m.location.latitude)) < :meterDistance
        """,
    )
    fun findByDistance(longitude: BigDecimal, latitude: BigDecimal, meterDistance: Int): List<Mission>
}