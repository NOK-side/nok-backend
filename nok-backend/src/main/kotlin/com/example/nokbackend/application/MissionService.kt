package com.example.nokbackend.application

import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.misson.*
import com.example.nokbackend.domain.store.StoreRepository
import com.example.nokbackend.domain.store.findByIdCheck
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
    private val gifticonRepository: GifticonRepository,
    private val storeRepository: StoreRepository
) {

    fun findMissionGroupInfo(id: Long): MissionGroupInfoResponse {
        val missionGroup = missionGroupRepository.findByIdCheck(id)
        val gifticon = gifticonRepository.findByIdCheck(missionGroup.prizeId)
        val missions = missionRepository.findByMissionGroup(missionGroup)
        val store = storeRepository.findByIdCheck(gifticon.storeId)

        return MissionGroupInfoResponse(missionGroup, gifticon, missions, store)
    }


}