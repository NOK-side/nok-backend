package com.example.nokbackend.fixture

import com.example.nokbackend.domain.gifticon.Gifticon
import java.math.BigDecimal

const val gifticonName = "test gifticon"
const val gifticonNotice = "test notice"
const val gifticonRefundAndExchangeInstruction = "test refundAndExchangeInstruction"
const val period = 30L
const val orderCancellationPeriod = 30L
val gifticonPrice = BigDecimal(1000)
const val gifticonId = "test gifticon id"

fun aGifticon(): Gifticon = Gifticon(
    storeId = aStore().id,
    productName = gifticonName,
    period = period,
    notice = gifticonNotice,
    refundAndExchangeInstruction = gifticonRefundAndExchangeInstruction,
    price = gifticonPrice,
    category = Gifticon.Category.CAFE,
    status = Gifticon.Status.ACTIVE,
    imageUrl = "",
    orderCancellationPeriod = orderCancellationPeriod,
    gifticonId = gifticonId,
    recommend = false
)


