package com.example.nokbackend.domain.store

import com.example.nokbackend.domain.BaseEntity
import javax.persistence.*

@Entity
class StoreImage(
    @ManyToOne
    var store: Store,

    @Column
    var imageUrl: String,

    @Enumerated(value = EnumType.STRING)
    var status: Status,

    id: Long = 0L
) : BaseEntity(id) {

    enum class Status { ACTIVE, INACTIVE }
}