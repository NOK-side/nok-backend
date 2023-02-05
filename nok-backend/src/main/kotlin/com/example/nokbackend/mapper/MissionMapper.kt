package com.example.nokbackend.mapper

import com.example.nokbackend.application.gifticon.GifticonResponse
import com.example.nokbackend.application.mission.MissionGroupInfoResponse
import com.example.nokbackend.application.mission.MissionInfoResponse
import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.membermission.MemberMission
import com.example.nokbackend.domain.membermission.MemberMissionGroup
import com.example.nokbackend.domain.misson.Mission
import com.example.nokbackend.domain.misson.MissionGroup
import com.example.nokbackend.domain.store.Store
import com.example.nokbackend.domain.toHashmapByIdAsKey
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class MissionMapper {

    fun toMissionGroupInfoResponse(
        missionGroup: MissionGroup,
        gifticon: Gifticon,
        store: Store,
        missions: List<Mission>,
        memberMissionGroup: MemberMissionGroup?,
        memberMissions: List<MemberMission>
    ): MissionGroupInfoResponse {
        return MissionGroupInfoResponse(
            id = missionGroup.id,
            title = missionGroup.title,
            subTitle = missionGroup.subTitle,
            description = missionGroup.description,
            prizeInfo = GifticonResponse(gifticon, store),
            imageUrl = missionGroup.imageUrl,
            location = missionGroup.location,
            missionInfoResponses = missions.map {
                val memberMissionMap = toHashmapByMissionIdAsKey(memberMissions)
                MissionInfoResponse(it, memberMissionMap[it.id])
            },
            status = memberMissionGroup?.status ?: MemberMissionGroup.Status.NOTHING,
            dueDate = memberMissionGroup?.dueDate ?: LocalDate.MAX
        )
    }

    fun toMissionGroupInfoResponses(
        missionGroups: List<MissionGroup>,
        gifticons: List<Gifticon>,
        stores: List<Store>,
        memberMissionGroups: List<MemberMissionGroup>,
        missions: List<Mission>,
        memberMissions: List<MemberMission>
    ) = missionGroups.map {
        val gifticon = toHashmapByIdAsKey(gifticons)[it.prizeId]!!
        val store = toHashmapByIdAsKey(stores)[gifticon.id]!!
        val memberMissionGroup = memberMissionGroups.firstOrNull { memberMissionGroup -> memberMissionGroup.missionGroupId == it.id }

        MissionGroupInfoResponse(
            missionGroup = it,
            gifticon = gifticon,
            store = store,
            missions = missions.filter { mission -> mission.missionGroup == it },
            memberMissionGroup = memberMissionGroup,
            memberMissions = memberMissions.filter { memberMission -> memberMission.memberMissionGroup == memberMissionGroup }
        )
    }

    private fun toHashmapByMissionIdAsKey(targets: List<MemberMission>): HashMap<Long, MemberMission> {
        val entityMap = hashMapOf<Long, MemberMission>()

        targets.forEach { entityMap[it.missionId] = it }

        return entityMap
    }

}