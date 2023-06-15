package com.example.nokbackend.domain.order

import com.example.nokbackend.domain.BaseEntity
import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Orders(
    @Column
    val orderMemberId: Long,

    @Column
    val totalPoint: BigDecimal,

    @CreatedDate
    var createDate: LocalDate = LocalDate.now(),

    id: Long = 0L,
) : BaseEntity(id) {
}