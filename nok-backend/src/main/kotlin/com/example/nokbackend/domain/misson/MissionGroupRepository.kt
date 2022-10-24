package com.example.nokbackend.domain.misson

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun MissionGroupRepository.findByIdCheck(id: Long): MissionGroup = findByIdOrNull(id) ?: throw RuntimeException("등록된 미션그룹이 없습니다")

interface MissionGroupRepository: JpaRepository<MissionGroup, Long> {

    fun findByTouristSpotId(id: Long): List<MissionGroup>
}