package com.example.nokbackend.application.gifticon

import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.infra.Point
import com.example.nokbackend.domain.store.Store

data class FindGifticonCondition(
    val name: String,
)

data class RegisterGifticonRequest(
    val productName: String,
    val period: Long,
    val notice: String,
    val refundAndExchangeInstruction: String,
    val price: Point,
    val category: Gifticon.Category = Gifticon.Category.NOTHING,
    val status: Gifticon.Status = Gifticon.Status.ACTIVE,
    val imageUrl: String,
    val orderCancellationPeriod: Long,
    val recommend: Boolean,
) {
    fun toEntity(storeId: Long, gifticonId: String): Gifticon {
        return Gifticon(storeId, productName, period, notice, refundAndExchangeInstruction, price, category, status, imageUrl, orderCancellationPeriod, gifticonId, recommend)
    }
}

data class GifticonResponse(
    val id: Long,
    val productName: String,
    val price: Point,
    val notice: String,
    val category: Gifticon.Category,
    val imageUrl: String,
    val period: Long,
    val storeName: String,
    val recommend: Boolean,
) {
    constructor(gifticon: Gifticon, store: Store) : this(
        gifticon.id,
        gifticon.productName,
        gifticon.price,
        gifticon.notice,
        gifticon.category,
        gifticon.imageUrl,
        gifticon.period,
        store.name,
        gifticon.recommend
    )
}

data class GifticonDetailResponse(
    val id: Long,
    val productName: String,
    val period: Long,
    val notice: String,
    val refundAndExchangeInstruction: String,
    val price: Point,
    val category: Gifticon.Category = Gifticon.Category.NOTHING,
    val status: Gifticon.Status = Gifticon.Status.INACTIVE,
    val imageUrl: String,
    val orderCancellationPeriod: Long,
    val storeId: Long,
    val storeName: String,
    val recommend: Boolean,
) {
    constructor(gifticon: Gifticon, store: Store) : this(
        id = gifticon.id,
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
        storeName = store.name,
        recommend = gifticon.recommend
    )

    companion object {
        fun empty() = GifticonDetailResponse(
            id = 0L,
            productName = "",
            period = 0L,
            notice = "",
            refundAndExchangeInstruction = "",
            price = Point(0),
            imageUrl = "",
            orderCancellationPeriod = 0L,
            storeId = 0L,
            storeName = "",
            recommend = false
        )
    }
}
