package com.example.nokbackend.domain.misson

import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.domain.infra.Location
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class MissionGroup(
    @Column
    var subTitle: String,

    @Column
    var title: String,

    @Column
    var description: String,

    @Column
    var prizeId: Long,

    @Column
    var location: Location,

    @Column
    var imageUrl: String,

    id: Long = 0L
): BaseEntity(id) {
}