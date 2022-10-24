package com.example.nokbackend.domain.membermission

import com.example.nokbackend.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class MemberMission(
    @ManyToOne
    val memberMissionGroup: MemberMissionGroup,

    val status: Status,

    id: Long = 0L
) : BaseEntity(id) {
    enum class Status { NOTHING, PROCESS, FINISHED, FAILED }
}