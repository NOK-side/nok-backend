package com.example.nokbackend.domain.memberpoint

import com.example.nokbackend.domain.BaseEntity
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class MemberPoint(
    @Column
    val memberId: Long,

    @Column
    var point: BigDecimal,

    id: Long = 0L
) : BaseEntity(id) {

}