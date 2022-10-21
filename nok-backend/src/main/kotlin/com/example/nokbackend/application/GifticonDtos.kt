package com.example.nokbackend.application

import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.store.Store
import java.math.BigDecimal

data class FindGifticonCondition(
    val name: String
)

data class RegisterGifticonRequest(
    val productName: String,
    val period: Long,
    val notice: String,
    val refundAndExchangeInstruction: String,
    val price: BigDecimal,
    val category: Gifticon.Category = Gifticon.Category.NOTHING,
    val status: Gifticon.Status = Gifticon.Status.ACTIVE,
    val imageUrl: String,
    val orderCancellationPeriod: Long
) {
    fun toEntity(storeId: Long): Gifticon {
        return Gifticon(storeId, productName, period, notice, refundAndExchangeInstruction, price, category, status, imageUrl, orderCancellationPeriod)
    }
}

data class GifticonResponse(
    val id: Long,
    val productName: String,
    val price: BigDecimal,
    val notice: String,
    val category: Gifticon.Category,
    val imageUrl: String,
    val period: Long,
    val storeName: String
) {
    constructor(gifticon: Gifticon, store: Store) : this(
        gifticon.id,
        gifticon.productName,
        gifticon.price,
        gifticon.notice,
        gifticon.category,
        gifticon.imageUrl,
        gifticon.period,
        store.name
    )
}

data class GifticonDetailResponse(
    val productName: String,
    val period: Long,
    val description: String,
    val price: BigDecimal,
    val category: Gifticon.Category,
    val imageUrl: String
) {
    constructor(gifticon: Gifticon) : this(
        gifticon.productName,
        gifticon.period,
        gifticon.refundAndExchangeInstruction,
        gifticon.price,
        gifticon.category,
        gifticon.imageUrl
    )
}