package com.example.nokbackend.domain.misson

import com.example.nokbackend.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Example(
    @ManyToOne
    val question: Question,

    var example: String,

    id: Long = 0L
) : BaseEntity(id) {
}