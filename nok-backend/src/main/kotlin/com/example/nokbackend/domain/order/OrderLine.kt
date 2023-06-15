package com.example.nokbackend.domain.order

import com.example.nokbackend.domain.BaseEntity
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne

@Entity
class OrderLine(
    @ManyToOne
    val orders: Orders,

    @Column
    val gifticonId: Long,

    @Column
    val quantity: Int,

    @Column
    val price: BigDecimal,

    @Enumerated(value = EnumType.STRING)
    var status: Status,

    id: Long = 0L,
) : BaseEntity(id) {

    enum class Status {
        ORDER, CANCEL
    }
}