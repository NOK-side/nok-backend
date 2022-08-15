package com.example.nokbackend

import com.example.nokbackend.domain.memberGifticon.MemberGifticon
import org.junit.jupiter.api.Test
import java.time.LocalDate


class ApplicationTests {

    @Test
    fun contextLoads() {

        val memberGifticon1 = MemberGifticon(1, 1, LocalDate.now(), MemberGifticon.Status.ACTIVE, LocalDate.now(), LocalDate.now())

        memberGifticon1
    }

}
