package com.example.nokbackend.fixture

import com.example.nokbackend.domain.memberGifticon.MemberGifticon
import java.time.LocalDate

fun aMemberGifticon(
    id: Long = 1L,
    ownerId: Long = aMember().id,
    gifticonId: Long = aGifticon().id,
    dueDate: LocalDate = LocalDate.now().plusDays(aGifticon().period),
    status: MemberGifticon.Status = MemberGifticon.Status.ACTIVE
): MemberGifticon = MemberGifticon(
    id = id,
    ownerId = ownerId,
    gifticonId = gifticonId,
    dueDate = dueDate,
    status = status
)