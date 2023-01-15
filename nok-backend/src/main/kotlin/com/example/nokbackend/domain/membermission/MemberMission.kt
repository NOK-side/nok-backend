package com.example.nokbackend.domain.membermission

import com.example.nokbackend.domain.BaseEntity
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne

@Entity
class MemberMission(
    @ManyToOne
    val memberMissionGroup: MemberMissionGroup,

    val missionId: Long,

    @Enumerated(value = EnumType.STRING)
    var status: Status,

    id: Long = 0L
) : BaseEntity(id) {

    fun complete() {
        this.status =  Status.FINISHED
    }
    enum class Status { NOTHING, PROGRESSING, FINISHED, FAILED }
}