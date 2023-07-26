package com.example.nokbackend.domain.order

import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.domain.infra.Point
import javax.persistence.*

@Entity
class OrderLine(
    @ManyToOne
    val order: Order,

    @Column
    val gifticonId: Long,

    @Column
    val quantity: Int,

    @Column
    val price: Point,

    @Enumerated(value = EnumType.STRING)
    var status: Status,

    id: Long = 0L,
) : BaseEntity(id) {

    enum class Status {
        ORDER, CANCEL
    }
}