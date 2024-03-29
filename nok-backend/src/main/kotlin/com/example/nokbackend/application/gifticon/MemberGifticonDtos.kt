package com.example.nokbackend.application.gifticon

import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.infra.Point
import com.example.nokbackend.domain.membergifticon.MemberGifticon
import com.example.nokbackend.domain.store.Store
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class BuyGifticonRequest(
    val gifticonId: Long,
    val quantity: Int,
)

data class BuyGifticonInCartRequest(
    val cartIds: List<Long>,
)

data class MemberGifticonResponse(
    val memberGifticonId: Long,
    val gifticonId: Long,
    val gifticonName: String,
    val price: Point,
    val category: Gifticon.Category,
    val imageUrl: String,
    val orderId: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    val dueDate: LocalDate,
    val status: MemberGifticon.Status,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    val createDate: LocalDate,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    val modifiedDate: LocalDate,
    val storeId: Long,
    val storeName: String,
) {
    constructor(gifticon: Gifticon, memberGifticon: MemberGifticon, store: Store) : this(
        memberGifticon.id,
        memberGifticon.gifticonId,
        gifticon.productName,
        gifticon.price,
        gifticon.category,
        gifticon.imageUrl,
        memberGifticon.orderId,
        memberGifticon.dueDate,
        memberGifticon.status,
        memberGifticon.createDate,
        memberGifticon.modifiedDate,
        store.id,
        store.name
    )
}

data class SendGifticonRequest(
    val targetMemberId: String,
    val memberGifticonId: Long,
)

data class UseGifticonRequest(
    val memberGifticonId: Long,
)

data class TransferGifticonRequest(
    val targetId: Long,
    val targetDueDate: LocalDate,
    val newOrderId: String,
    val orderCancellationDueDate: LocalDate,
)