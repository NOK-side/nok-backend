package com.example.nokbackend.application

import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.misson.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MissionService(
    private val missionGroupRepository: MissionGroupRepository,
    private val missionRepository: MissionRepository,
    private val questionGroupRepository: QuestionGroupRepository,
    private val questionRepository: QuestionRepository,
    private val exampleRepository: ExampleRepository,
    private val gifticonRepository: GifticonRepository
) {

    fun findMissionGroupInfo(id: Long): MissionGroupInfoResponse {
        val missionGroup = missionGroupRepository.findByIdCheck(id)
        val gifticon = gifticonRepository.findByIdCheck(missionGroup.prizeId)
        val missions = missionRepository.findByMissionGroup(missionGroup)

        return MissionGroupInfoResponse(missionGroup, gifticon, missions)
    }


}