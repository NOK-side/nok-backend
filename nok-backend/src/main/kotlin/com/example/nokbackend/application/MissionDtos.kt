package com.example.nokbackend.application

import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.misson.Mission
import com.example.nokbackend.domain.misson.MissionGroup

data class MissionGroupInfoResponse(
    val id: Long,
    val description: String,
    val prizeInfo: GifticonResponse,
    val imageUrl: String,
    val missionInfoResponses: List<MissionInfoResponse>
) {
    constructor(missionGroup: MissionGroup, gifticon: Gifticon, missions: List<Mission>) : this(
        id = missionGroup.id,
        description = missionGroup.description,
        prizeInfo = GifticonResponse(gifticon),
        imageUrl = missionGroup.imageUrl,
        missionInfoResponses = missions.map { MissionInfoResponse(it) }
    )
}

data class MissionInfoResponse(
    val id: Long,
    val title: String,
    val imageUrl: String,
) {
    constructor(mission: Mission) : this(
        id = mission.id,
        title = mission.title,
        imageUrl = mission.imageUrl
    )
}