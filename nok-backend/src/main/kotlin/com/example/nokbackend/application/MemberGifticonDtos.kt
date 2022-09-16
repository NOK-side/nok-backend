package com.example.nokbackend.application

import com.example.nokbackend.domain.model.gifticon.Gifticon
import com.example.nokbackend.domain.model.memberGifticon.MemberGifticon
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
    val dueDate: LocalDate,
    val status: MemberGifticon.Status,
    val createDate: LocalDate,
    val modifiedDate: LocalDate
) {
    constructor(gifticon: Gifticon, memberGifticon: MemberGifticon) : this(
        memberGifticon.id,
        memberGifticon.gifticonId,
        gifticon.productName,
        gifticon.price,
        gifticon.category,
        memberGifticon.dueDate,
        memberGifticon.status,
        memberGifticon.createDate,
        memberGifticon.modifiedDate
    )
}

data class SendGifticonRequest(
    val targetMemberId: String,
    val memberGifticonId: Long
)

data class UseGifticonRequest(
    val memberGifticonId: Long
)