package com.example.nokbackend.domain.misson

import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.domain.infra.Location
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne

@Entity
class Mission(
    @ManyToOne
    val missionGroup: MissionGroup,

    @Column
    var title: String,

    @Column
    var subTitle: String,

    @Column
    var description: String,

    @Column
    var location: Location,

    @Enumerated(EnumType.STRING)
    val type: Type,

    @Column
    var qualification: Int,

    @Column
    var imageUrl: String,

    @Column
    var formId: String,

    @Column
    var questionUrl: String,

    id: Long = 0L
) : BaseEntity(id) {

    enum class Type { QR_WITH_QUESTION, CURRENT_USER_LOCATION }
}