package com.example.nokbackend.domain.misson

import com.example.nokbackend.domain.BaseEntity
import javax.persistence.Entity

@Entity
class MissionGroup(
    var title: String,

    var description: String,

    var priceId: Long,

    var imageUrl: String,

    id: Long = 0L
): BaseEntity(id) {
}