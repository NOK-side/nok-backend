package com.example.nokbackend.domain.misson

import org.springframework.data.jpa.repository.JpaRepository

interface MissionRepository: JpaRepository<Mission, Long> {

    fun findByMissionGroup(missionGroup: MissionGroup): List<Mission>
}