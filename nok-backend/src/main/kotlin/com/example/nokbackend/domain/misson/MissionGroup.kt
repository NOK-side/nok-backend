package com.example.nokbackend.domain.misson

import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.domain.Location
import javax.persistence.Entity

@Entity
class MissionGroup(
    var touristSpotId: Long,

    var subTitle: String,

    var title: String,

    var description: String,

    var prizeId: Long,

    var location: Location,

    var imageUrl: String,

    id: Long = 0L
): BaseEntity(id) {
}