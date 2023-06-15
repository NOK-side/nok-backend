package com.example.nokbackend.domain.memberpoint

import com.example.nokbackend.domain.BaseEntity
import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDate
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
    var createDate: LocalDate = LocalDate.now(),

    @Column
    val point: BigDecimal,

    @Enumerated(value = EnumType.STRING)
    val payMethod: PayMethod,

    @Column
    val payInfo: String,

    id: Long = 0L
) : BaseEntity(id) {

    enum class PayMethod {
        CARD, ACCOUNT, PHONE, EASY_PAYMENT
    }
}