package com.example.nokbackend.domain.store

import com.example.nokbackend.infra.BaseEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne

@Entity
class StoreImage(
    @ManyToOne
    var store: Store,

    var imageUrl: String,

    @Enumerated(value = EnumType.STRING)
    var status: Status,

    id: Long = 0L
) : BaseEntity(id) {

    enum class Status { ACTIVE, INACTIVE }
}