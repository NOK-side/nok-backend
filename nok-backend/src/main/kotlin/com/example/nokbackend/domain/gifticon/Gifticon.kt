package com.example.nokbackend.domain.gifticon

import com.example.nokbackend.domain.BaseEntity
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Lob

@Entity
class Gifticon(
    val storeId: Long,

    var productName: String,

    var period: Long,

    @Lob
    var description: String,

    var price: BigDecimal,

    @Enumerated(value = EnumType.STRING)
    var category: Category = Category.NOTHING,

    @Enumerated(value = EnumType.STRING)
    var status: Status = Status.INACTIVE,

    var imageUrl: String,

    id: Long = 0L
) : BaseEntity(id) {

    enum class Category { NOTHING, CAFE, RESTAURANT }

    enum class Status { ACTIVE, INACTIVE }
}