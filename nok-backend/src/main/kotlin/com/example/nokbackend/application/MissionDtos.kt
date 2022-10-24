package com.example.nokbackend.application

import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.membermission.MemberMission
import com.example.nokbackend.domain.membermission.MemberMissionGroup
import com.example.nokbackend.domain.misson.Mission
import com.example.nokbackend.domain.misson.MissionGroup
import com.example.nokbackend.domain.store.Store
import com.example.nokbackend.domain.toHashmapByIdAsKey

data class MissionGroupInfoResponse(
    val id: Long,
    val title: String,
    val subTitle: String,
    val description: String,
    val prizeInfo: GifticonResponse,
    val imageUrl: String,
    val missionInfoResponses: List<MissionInfoResponse>,
    val status: MemberMissionGroup.Status
) {
    constructor(missionGroup: MissionGroup, gifticon: Gifticon, store: Store, missions: List<Mission>, memberMissionGroup: MemberMissionGroup?, memberMissions: List<MemberMission>) : this(
        id = missionGroup.id,
        title = missionGroup.title,
        subTitle = missionGroup.subTitle,
        description = missionGroup.description,
        prizeInfo = GifticonResponse(gifticon, store),
        imageUrl = missionGroup.imageUrl,
        missionInfoResponses = missions.map {
            val memberMissionMap = toHashmapByIdAsKey(memberMissions)
            MissionInfoResponse(it, memberMissionMap[it.id])
        },
        status = memberMissionGroup?.status ?: MemberMissionGroup.Status.NOTHING
    )

}

data class MissionInfoResponse(
    val id: Long,
    val title: String,
    val subTitle: String,
    val description: String,
    val imageUrl: String,
    val status: MemberMission.Status
) {
    constructor(mission: Mission, memberMission: MemberMission?) : this(
        id = mission.id,
        title = mission.title,
        subTitle = mission.subTitle,
        description = mission.description,
        imageUrl = mission.imageUrl,
        status = memberMission?.status ?: MemberMission.Status.NOTHING
    )
}

