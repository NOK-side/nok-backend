package com.example.nokbackend.domain.store

import com.example.nokbackend.infra.BaseEntity
import javax.persistence.Embedded
import javax.persistence.Entity

@Entity
class Store(
    val ownerId: Long,

    var name: String,

    var category: Category = Category.DEFAULT,

    @Embedded
    var address: Address,

    var status: Status = Status.READY,

    id: Long = 0L
) : BaseEntity(id) {

    enum class Category { DEFAULT, CAFE, RESTAURANT, }

    enum class Status { READY, ACTIVE, INACTIVE, }
}