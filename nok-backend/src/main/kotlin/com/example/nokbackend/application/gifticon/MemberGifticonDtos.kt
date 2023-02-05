package com.example.nokbackend.application.gifticon

import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.memberGifticon.MemberGifticon
import com.example.nokbackend.domain.store.Store
import java.math.BigDecimal
import java.time.LocalDate

data class BuyGifticonRequest(
    val gifticonId: Long,
    val quantity: Int
)

data class BuyGifticonInCartRequest(
    val cartIds: List<Long>
)

data class MemberGifticonResponse(
    val memberGifticonId: Long,
    val gifticonId: Long,
    val gifticonName: String,
    val price: BigDecimal,
    val category: Gifticon.Category,
    val orderId: String,
    val dueDate: LocalDate,
    val status: MemberGifticon.Status,
    val createDate: LocalDate,
    val modifiedDate: LocalDate,
    val storeName: String
) {
    constructor(gifticon: Gifticon, memberGifticon: MemberGifticon, store: Store) : this(
        memberGifticon.id,
        memberGifticon.gifticonId,
        gifticon.productName,
        gifticon.price,
        gifticon.category,
        memberGifticon.orderId,
        memberGifticon.dueDate,
        memberGifticon.status,
        memberGifticon.createDate,
        memberGifticon.modifiedDate,
        store.name
    )
}

data class SendGifticonRequest(
    val targetMemberId: String,
    val memberGifticonId: Long
)

data class UseGifticonRequest(
    val memberGifticonId: Long
)

data class TransferGifticonRequest(
    val targetId: Long,
    val targetDueDate: LocalDate,
    val newOrderId: String,
    val orderCancellationDueDate: LocalDate
)