package com.example.nokbackend.domain.membermission

import com.example.nokbackend.domain.BaseEntity
import javax.persistence.*

@Entity
class MemberMission(
    @ManyToOne
    val memberMissionGroup: MemberMissionGroup,

    @Column
    val missionId: Long,

    @Enumerated(value = EnumType.STRING)
    var status: Status,

    id: Long = 0L,
) : BaseEntity(id) {

    fun complete() {
        this.status = Status.FINISHED
    }

    enum class Status { NOTHING, ONGOING, FINISHED, FAILED }
}