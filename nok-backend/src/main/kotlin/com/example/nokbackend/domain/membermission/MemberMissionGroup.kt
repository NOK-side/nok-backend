package com.example.nokbackend.domain.membermission

import com.example.nokbackend.domain.BaseEntity
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class MemberMissionGroup(
    @Column
    val memberId: Long,

    @Column
    val missionGroupId: Long,

    @Enumerated(value = EnumType.STRING)
    var status: Status,

    @Column
    val dueDate: LocalDate,

    id: Long = 0L
) : BaseEntity(id) {
    fun complete() {
        status = Status.FINISHED
    }

    enum class Status { NOTHING, ONGOING, FINISHED, FAILED }
}