package com.example.nokbackend.domain.membermission

import com.example.nokbackend.domain.BaseEntity
import javax.persistence.Entity

@Entity
class MemberMissionGroup(
    val memberId: Long,

    val missionGroupId: Long,

    var status: Status,

    id: Long = 0L
) : BaseEntity(id) {
    fun complete() {
        status = Status.FINISHED
    }

    enum class Status { NOTHING, PROCESS, FINISHED, FAILED }
}