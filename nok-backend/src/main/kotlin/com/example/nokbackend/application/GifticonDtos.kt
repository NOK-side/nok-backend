package com.example.nokbackend.application

import com.example.nokbackend.domain.gifticon.Gifticon
import java.math.BigDecimal

data class FindGifticonCondition(
    val name: String
)

data class RegisterGifticonRequest(
    val productName: String,
    val period: Long,
    val description: String,
    val price: BigDecimal,
    val category: Gifticon.Category = Gifticon.Category.NOTHING,
    val status: Gifticon.Status = Gifticon.Status.ACTIVE
) {
    fun toEntity(storeId: Long): Gifticon {
        return Gifticon(storeId, productName, period, description, price, category, status)
    }
}

data class GifticonResponse(
    val productName: String,
    val price: BigDecimal,
    val category: Gifticon.Category
) {
    constructor(gifticon: Gifticon) : this(
        gifticon.productName,
        gifticon.price,
        gifticon.category
    )
}

data class GifticonDetailResponse(
    val productName: String,
    val period: Long,
    val description: String,
    val price: BigDecimal,
    val category: Gifticon.Category
) {
    constructor(gifticon: Gifticon) : this(
        gifticon.productName,
        gifticon.period,
        gifticon.description,
        gifticon.price,
        gifticon.category
    )
}