package com.example.nokbackend.domain.gifticon

import com.example.nokbackend.infra.BaseEntity
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Lob

@Entity
class Gifticon(
    val storeId: Long,

    val productName: String,

    val period: Int,

    @Lob
    val description: String,

    val price: BigDecimal,

    @Enumerated(value = EnumType.STRING)
    val category: Category = Category.NOTHING,

    @Enumerated(value = EnumType.STRING)
    val status: Status = Status.INACTIVE,

    id: Long = 0L
) : BaseEntity(id) {

    enum class Category { NOTHING, CAFE, RESTAURANT }

    enum class Status { ACTIVE, INACTIVE }
}