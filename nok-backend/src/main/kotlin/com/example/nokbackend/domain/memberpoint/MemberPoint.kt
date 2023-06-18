package com.example.nokbackend.domain.memberpoint

import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.domain.infra.Point
import javax.persistence.AttributeOverride
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity

@Entity
class MemberPoint(
    @Column
    val memberId: Long,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "point"))
    var point: Point,

    id: Long = 0L,
) : BaseEntity(id) {

}