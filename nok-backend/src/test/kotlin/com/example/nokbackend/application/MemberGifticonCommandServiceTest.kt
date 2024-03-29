package com.example.nokbackend.application

import com.example.nokbackend.application.gifticon.BuyGifticonRequest
import com.example.nokbackend.application.gifticon.MemberGifticonCommandService
import com.example.nokbackend.application.gifticon.SendGifticonRequest
import com.example.nokbackend.application.gifticon.UseGifticonRequest
import com.example.nokbackend.application.util.UUIDGenerator
import com.example.nokbackend.domain.gifticon.GifticonRepository
import com.example.nokbackend.domain.gifticon.findByIdCheck
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberInformation
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.member.findByMemberIdCheck
import com.example.nokbackend.domain.membergifticon.MemberGifticon
import com.example.nokbackend.domain.membergifticon.MemberGifticonRepository
import com.example.nokbackend.domain.membergifticon.findByIdCheck
import com.example.nokbackend.fixture.*
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class MemberGifticonCommandServiceTest {
    @MockK
    private lateinit var memberGifticonRepository: MemberGifticonRepository

    @MockK
    private lateinit var gifticonRepository: GifticonRepository

    @MockK
    private lateinit var memberRepository: MemberRepository

    @MockK
    private lateinit var memberGifticonCommandService: MemberGifticonCommandService

    @MockK
    private lateinit var uuidGenerator: UUIDGenerator

    @BeforeEach
    internal fun setUp() {
        memberGifticonCommandService = MemberGifticonCommandService(
            memberGifticonRepository,
            gifticonRepository,
            memberRepository,
            uuidGenerator
        )
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
        every { uuidGenerator.generate(8) } returns "abcdefgh"

        slot<List<MemberGifticon>>().also { slot ->
            every { memberGifticonRepository.saveAll(capture(slot)) } answers {
                slot.captured.run {
                    map {
                        MemberGifticon(
                            id = it.id,
                            orderId = "abcdef",
                            ownerId = it.ownerId,
                            gifticonId = it.gifticonId,
                            dueDate = it.dueDate,
                            status = it.status,
                            orderCancellationDueDate = LocalDate.now().plusDays(aGifticon().orderCancellationPeriod)
                        )
                    }
                }
            }
        }

        memberGifticonCommandService.registerMemberGifticon(member, buyGifticonRequest)
    }

    @DisplayName("기프티콘 선물은")
    @Nested
    inner class SendGifticon {

        private fun subject() {
            every { uuidGenerator.generate(8) } returns "abcdefgh"
            memberGifticonCommandService.sendGifticon(member, sendGifticonRequest)
        }

        @Test
        @DisplayName("본인 기프티콘을 다른 사람에게 보내면 성공한다")
        fun sendGifticonSuccess() {
            member = aMember()
            sendGifticonRequest = SendGifticonRequest(targetMemberId, 1L)

            every { memberGifticonRepository.findByIdCheck(any()) } returns aMemberGifticon()
            every { gifticonRepository.findByIdCheck(any()) } returns aGifticon()
            every { memberRepository.findByMemberIdCheck(any()) } returns aMember(
                information = MemberInformation(
                    targetMemberId,
                    email,
                    name,
                    phoneNumber,
                    ""
                )
            )
            every { memberGifticonRepository.save(any()) } returns aMemberGifticon()

            subject()
        }

        @Test
        @DisplayName("본인 기프티콘이 아니면 실패한다")
        fun sendGifticonFail1() {
            member = aMember()
            sendGifticonRequest = SendGifticonRequest(targetMemberId, 1L)

            every { memberGifticonRepository.findByIdCheck(any()) } returns aMemberGifticon(ownerId = 2L)
            every { gifticonRepository.findByIdCheck(any()) } returns aGifticon()
            every { memberRepository.findByMemberIdCheck(any()) } returns aMember().apply {
                information = MemberInformation(targetMemberId, email, name, phoneNumber, "")
            }
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

    @DisplayName("기프티콘 사용은")
    @Nested
    inner class UseGifticon {
        private lateinit var member: Member
        private lateinit var useGifticonRequest: UseGifticonRequest
        private lateinit var memberGifticon: MemberGifticon

        private fun subject() {
            memberGifticonCommandService.useGifticon(member, useGifticonRequest)
        }

        @Test
        @DisplayName("적절한 기프티콘이라면 성공한다")
        fun useGifticonSuccess() {
            member = aMember()
            useGifticonRequest = UseGifticonRequest(1L)
            memberGifticon = aMemberGifticon()

            every { memberGifticonRepository.findByIdCheck(any()) } returns memberGifticon

            subject()

            assertThat(memberGifticon.status).isEqualTo(MemberGifticon.Status.USED)
        }

        @Test
        @DisplayName("기간이 만료하면 실패한다")
        fun useGifticonFail1() {
            member = aMember()
            useGifticonRequest = UseGifticonRequest(1L)
            memberGifticon = aMemberGifticon(dueDate = LocalDate.now().minusDays(1))

            every { memberGifticonRepository.findByIdCheck(any()) } returns memberGifticon

            assertThrows<IllegalStateException> {
                subject()
            }
        }

        @Test
        @DisplayName("사용가능 상태가 아닌 기프티콘이면 실패한다")
        fun useGifticonFail2() {
            member = aMember()
            useGifticonRequest = UseGifticonRequest(1L)
            memberGifticon = aMemberGifticon(status = MemberGifticon.Status.USED)

            every { memberGifticonRepository.findByIdCheck(any()) } returns memberGifticon

            assertThrows<IllegalStateException> {
                subject()
            }
        }
    }
}