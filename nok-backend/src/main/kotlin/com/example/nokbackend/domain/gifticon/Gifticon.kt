package com.example.nokbackend.domain.gifticon

import com.example.nokbackend.domain.BaseEntity
import java.math.BigDecimal
import javax.persistence.*

@Entity
class Gifticon(
    @Column
    val storeId: Long,

    @Column
    var productName: String,

    @Column
    var period: Long,

    @Lob
    var notice: String,

    @Lob
    var refundAndExchangeInstruction: String,

    @Column
    var price: BigDecimal,

    @Enumerated(value = EnumType.STRING)
    var category: Category = Category.NOTHING,

    @Enumerated(value = EnumType.STRING)
    var status: Status = Status.INACTIVE,

    @Column
    var imageUrl: String,

    @Column
    var orderCancellationPeriod: Long,

    @Column
    val gifticonId: String,

    @Column
    var recommend: Boolean,

    id: Long = 0L
) : BaseEntity(id) {

    enum class Category { NOTHING, CAFE, RESTAURANT }

    enum class Status { ACTIVE, INACTIVE }
}