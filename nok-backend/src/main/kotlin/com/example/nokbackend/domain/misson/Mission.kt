package com.example.nokbackend.domain.misson

import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.domain.Address
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne

@Entity
class Mission(
    @ManyToOne
    val missionGroup: MissionGroup,

    var title: String,

    var imageUrl: String,

    var description: String,

    var location: Address,

    @Enumerated(EnumType.STRING)
    val type: Type,

    var qualification: Int,

    id: Long = 0L
) : BaseEntity(id) {

    enum class Type { QR_WITH_QUESTION, CURRENT_USER_LOCATION }
}