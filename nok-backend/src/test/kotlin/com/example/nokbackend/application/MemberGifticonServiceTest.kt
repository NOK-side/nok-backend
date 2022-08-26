package com.example.nokbackend.application

import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberInformation
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.member.findByMemberIdCheck
import com.example.nokbackend.domain.memberGifticon.MemberGifticon
import com.example.nokbackend.domain.memberGifticon.MemberGifticonRepository
import com.example.nokbackend.domain.memberGifticon.findByIdCheck
import com.example.nokbackend.fixture.*
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import java.lang.IllegalStateException

@ExtendWith(MockKExtension::class)
class MemberGifticonServiceTest {
    @MockK
    private lateinit var memberGifticonRepository: MemberGifticonRepository

    @MockK
    private lateinit var gifticonRepository: GifticonRepository

    @MockK
    private lateinit var memberRepository: MemberRepository

    @MockK
    private lateinit var memberGifticonService: MemberGifticonService

    @BeforeEach
    internal fun setUp() {
        memberGifticonService = MemberGifticonService(memberGifticonRepository, gifticonRepository, memberRepository)
    }

    private val targetMemberId: String = "targetMemberId"
    private lateinit var member: Member
    private lateinit var sendGifticonRequest: SendGifticonRequest

    @Test
    @DisplayName("기프티콘을 구매한다")
    fun buyGifticon() {
        member = aMember()
        val buyGifticonRequest = BuyGifticonRequest(1L, 3)

        every { gifticonRepository.findByIdCheck(any()) } returns aGifticon()

        slot<List<MemberGifticon>>().also { slot ->
            every { memberGifticonRepository.saveAll(capture(slot)) } answers {
                slot.captured.run {
                    map {
                        MemberGifticon(
                            id = it.id,
                            ownerId = it.ownerId,
                            gifticonId = it.gifticonId,
                            dueDate = it.dueDate,
                            status = it.status
                        )
                    }
                }
            }
        }

        memberGifticonService.buyGifticon(member, buyGifticonRequest)
    }

    @DisplayName("기프티콘 선물은")
    @Nested
    inner class SendGifticon {

        private fun subject() {
            memberGifticonService.sendGifticon(member, sendGifticonRequest)
        }

        @Test
        @DisplayName("본인 기프티콘을 보내면 성공한다")
        fun sendGifticonSuccess() {
            member = aMember()
            sendGifticonRequest = SendGifticonRequest(targetMemberId, 1L)

            every { memberGifticonRepository.findByIdCheck(any()) } returns aMemberGifticon()
            every { gifticonRepository.findByIdCheck(any()) } returns aGifticon()
            every { memberRepository.findByMemberIdCheck(any()) } returns aMember(information = MemberInformation(targetMemberId, email, name, phoneNumber, ""))
            every { memberGifticonRepository.save(any()) } returns aMemberGifticon()

            subject()
        }

        @Test
        @DisplayName("본인 기프티콘이 아니면 실패한다")
        fun sendGifticonFail() {
            member = aMember()
            sendGifticonRequest = SendGifticonRequest(targetMemberId, 1L)

            every { memberGifticonRepository.findByIdCheck(any()) } returns aMemberGifticon(ownerId = 2L)
            every { gifticonRepository.findByIdCheck(any()) } returns aGifticon()
            every { memberRepository.findByMemberIdCheck(any()) } returns aMember().apply { information = MemberInformation(targetMemberId, email, name, phoneNumber, "") }
            every { memberGifticonRepository.save(any()) } returns aMemberGifticon()

            assertThrows<IllegalArgumentException> {
                subject()
            }
        }

        @Test
        @DisplayName("본인에게 선물하면 실패한다")
        fun sendGifticonFail2() {
            member = aMember()
            sendGifticonRequest = SendGifticonRequest(member.memberId, 1L)

            every { memberGifticonRepository.findByIdCheck(any()) } returns aMemberGifticon()
            every { gifticonRepository.findByIdCheck(any()) } returns aGifticon()
            every { memberRepository.findByMemberIdCheck(any()) } returns aMember()
            every { memberGifticonRepository.save(any()) } returns aMemberGifticon()

            assertThrows<IllegalStateException> {
                subject()
            }
        }
    }
}