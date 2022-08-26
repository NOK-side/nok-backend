package com.example.nokbackend.fixture

import com.example.nokbackend.domain.gifticon.Gifticon
import java.math.BigDecimal

const val gifticonName = "test gifticon"
const val gifticonDescription = "test description"
const val period = 30L
val gifticonPrice = BigDecimal(1000)

fun aGifticon(): Gifticon = Gifticon(
    storeId = aStore().id,
    productName = "test gifticon",
    period = period,
    description = gifticonDescription,
    price = gifticonPrice,
    category = Gifticon.Category.CAFE,
    status = Gifticon.Status.ACTIVE
)