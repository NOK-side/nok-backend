package com.example.nokbackend.application.gifticon

import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.store.Store
import java.math.BigDecimal
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Lob

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
    val notice: String,
    val refundAndExchangeInstruction: String,
    val price: BigDecimal,
    val category: Gifticon.Category = Gifticon.Category.NOTHING,
    val status: Gifticon.Status = Gifticon.Status.INACTIVE,
    val imageUrl: String,
    val orderCancellationPeriod: Long,
    val storeId: Long,
    val storeName: String
) {
    constructor(gifticon: Gifticon, store: Store) : this(
        productName = gifticon.productName,
        period = gifticon.period,
        notice = gifticon.notice,
        refundAndExchangeInstruction = gifticon.refundAndExchangeInstruction,
        price = gifticon.price,
        category = gifticon.category,
        status = gifticon.status,
        imageUrl = gifticon.imageUrl,
        orderCancellationPeriod = gifticon.orderCancellationPeriod,
        storeId = store.id,
        storeName = store.name
    )
}