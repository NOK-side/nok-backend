package com.example.nokbackend.application

import com.example.nokbackend.domain.Location
import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.membermission.MemberMission
import com.example.nokbackend.domain.membermission.MemberMissionGroup
import com.example.nokbackend.domain.misson.Mission
import com.example.nokbackend.domain.misson.MissionGroup
import com.example.nokbackend.domain.store.Store
import java.math.BigDecimal
import java.time.LocalDate

data class MissionGroupInfoResponse(
    val id: Long,
    val title: String,
    val subTitle: String,
    val description: String,
    val prizeInfo: GifticonResponse,
    val imageUrl: String,
    val location: Location,
    val missionInfoResponses: List<MissionInfoResponse>,
    val status: MemberMissionGroup.Status,
    val dueDate: LocalDate
) {
    constructor(missionGroup: MissionGroup, gifticon: Gifticon, store: Store, missions: List<Mission>, memberMissionGroup: MemberMissionGroup?, memberMissions: List<MemberMission>) : this(
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

private fun toHashmapByMissionIdAsKey(targets: List<MemberMission>): HashMap<Long, MemberMission> {
    val entityMap = hashMapOf<Long, MemberMission>()

    targets.forEach { entityMap[it.missionId] = it }

    return entityMap
}

data class MissionInfoResponse(
    val missionId: Long,
    val title: String,
    val subTitle: String,
    val description: String,
    val imageUrl: String,
    val type: Mission.Type,
    val memberMissionId: Long,
    val status: MemberMission.Status
) {
    constructor(mission: Mission, memberMission: MemberMission?) : this(
        missionId = mission.id,
        title = mission.title,
        subTitle = mission.subTitle,
        description = mission.description,
        imageUrl = mission.imageUrl,
        type = mission.type,
        memberMissionId = memberMission?.id ?: 0L,
        status = memberMission?.status ?: MemberMission.Status.NOTHING
    )
}

data class FindMissionGroupCondition(
    val city: String?,
    val keyword: String?,
    val longitude: BigDecimal = centerOfKoreaLongitude,
    val latitude: BigDecimal = centerOfKoreaLatitude,
    val distance: Int = defaultDistance
)

data class DistanceFromLocation(
    val longitude: BigDecimal,
    val latitude: BigDecimal,
    val distance: Int
)

data class FindCitiesRequest(
    val city: String
)


data class LocationAbbreviationWithLength(
    val roadNameAddress: String,
    val landNumberAddress: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
    val imageUrl: String
) {
    constructor(missionGroup: MissionGroup, length: Int) : this(
        roadNameAddress = missionGroup.location.roadNameAddress
            .split(" ")
            .take(length)
            .reduce { acc: String, s: String -> "$acc $s" },
        landNumberAddress = missionGroup.location.landNumberAddress
            .split(" ")
            .take(length)
            .reduce { acc: String, s: String -> "$acc $s" },
        latitude = missionGroup.location.latitude,
        longitude = missionGroup.location.longitude,
        imageUrl = missionGroup.imageUrl
    )
}

data class FindCitiesResponse(
    val cityName: String,
    val imageUrl: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal,
)

data class FormResult(
    val formId: String,
    val email: String,
    val results: List<AnswerResult>
)

data class AnswerResult(
    val id: String,
    val type: String,
    val title: String,
    val response: String,
    val score: Int
)