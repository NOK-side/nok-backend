package com.example.nokbackend.fixture

import com.example.nokbackend.domain.membergifticon.MemberGifticon
import java.time.LocalDate

fun aMemberGifticon(
    id: Long = 1L,
    orderId: String = "20221021ABCDEFGH",
    ownerId: Long = aMember().id,
    gifticonId: Long = aGifticon().id,
    dueDate: LocalDate = LocalDate.now().plusDays(aGifticon().period),
    status: MemberGifticon.Status = MemberGifticon.Status.ACTIVE,
    orderCancellationDueDate: LocalDate = LocalDate.now().plusDays(aGifticon().orderCancellationPeriod)
): MemberGifticon = MemberGifticon(
    id = id,
    orderId = orderId,
    ownerId = ownerId,
    gifticonId = gifticonId,
    dueDate = dueDate,
    status = status,
    orderCancellationDueDate = orderCancellationDueDate
)