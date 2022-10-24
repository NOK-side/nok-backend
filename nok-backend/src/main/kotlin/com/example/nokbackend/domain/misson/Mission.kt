package com.example.nokbackend.domain.misson

import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.domain.Location
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne

@Entity
class Mission(
    @ManyToOne
    val missionGroup: MissionGroup,

    var title: String,

    var subTitle: String,

    var description: String,

    var location: Location,

    @Enumerated(EnumType.STRING)
    val type: Type,

    var qualification: Int,

    var imageUrl: String,

    id: Long = 0L
) : BaseEntity(id) {

    enum class Type { QR_WITH_QUESTION, CURRENT_USER_LOCATION }
}