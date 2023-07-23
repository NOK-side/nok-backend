package com.example.nokbackend.domain.memberpoint

import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.domain.infra.Point
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class MemberPointCharge(
    @ManyToOne
    val memberPoint: MemberPoint,

    @Column
    val token: String,

    @Column
    val googleOrderId: String,

    @CreatedDate
    var createDate: LocalDateTime = LocalDateTime.now(),

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "point"))
    val point: Point,

    @Enumerated(value = EnumType.STRING)
    val payMethod: PayMethod,

    @Column
    val payInfo: String,

    id: Long = 0L,
) : BaseEntity(id) {

    enum class PayMethod {
        CARD, ACCOUNT, PHONE, EASY_PAYMENT
    }
}