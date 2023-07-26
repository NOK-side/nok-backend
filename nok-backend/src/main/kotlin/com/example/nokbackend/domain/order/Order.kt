package com.example.nokbackend.domain.order

import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.domain.infra.Point
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "orders")
class Order(
    @Column
    val orderMemberId: Long,

    @Column
    val totalPoint: Point,

    @CreatedDate
    var createDate: LocalDateTime = LocalDateTime.now(),

    id: Long = 0L,
) : BaseEntity(id) {
}