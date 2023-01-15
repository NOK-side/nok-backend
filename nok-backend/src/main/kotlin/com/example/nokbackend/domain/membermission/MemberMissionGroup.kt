package com.example.nokbackend.domain.membermission

import com.example.nokbackend.domain.BaseEntity
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class MemberMissionGroup(
    val memberId: Long,

    val missionGroupId: Long,

    @Enumerated(value = EnumType.STRING)
    var status: Status,

    val dueDate: LocalDate,

    id: Long = 0L
) : BaseEntity(id) {
    fun complete() {
        status = Status.FINISHED
    }

    enum class Status { NOTHING, PROCESS, FINISHED, FAILED }
}