package com.example.nokbackend

import com.example.nokbackend.domain.Location
import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.member.LoginInformation
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberInformation
import com.example.nokbackend.domain.member.Password
import com.example.nokbackend.domain.store.BusinessHour
import com.example.nokbackend.domain.store.Store
import com.example.nokbackend.domain.store.StoreInformation
import com.example.nokbackend.domain.touristspot.Facility
import com.example.nokbackend.domain.touristspot.TouristSpot
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaAuditing
@EnableAsync
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}


@Component
class InitService(
    private val init: Init
) {

    @PostConstruct
    fun init() {
        init.init()
    }

    companion object {
        @Component
        @Transactional
        class Init(private val em: EntityManager) {
            fun init() {
                val member = Member(
                    MemberInformation(
                        memberId = "rkdals2134",
                        email = "rkdals2134@naver.com",
                        name = "tester",
                        phoneNumber = "01000000000",
                        profileImage = "testImg",
                    ),
                    loginInformation = LoginInformation("", ""),
                    password = Password("1q2w3e4r"),
                    role = Member.Role.STORE,
                    status = Member.Status.ACTIVE
                )
                em.persist(member)

                for (i in 0..20) {
                    val store2 = Store(
                        i.toLong(),
                        StoreInformation(
                            "123123123",
                            "테스트 상점 $i",
                            StoreInformation.Category.RESTAURANT,
                            "000-0000-0000",
                            Location("도로명 주소", "지번 주소", BigDecimal(100), BigDecimal(100)),
                            "이것은 $i 번재 테스트 상점인데요 어쩔티비 저쩔티비 크크루삥뽕",
                            "",
                            BusinessHour(9, 18),
                            "SUNDAY",
                            "https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media"
                        ),
                        Store.Status.ACTIVE
                    )
                    em.persist(store2)
                }

                val store = Store(
                    1,
                    StoreInformation(
                        "123123123",
                        "콩새식당",
                        StoreInformation.Category.CAFE,
                        "000-0000-0000",
                        Location("강원 속초시 영금정로2길 9-1", "강원 속초시 동명동 53-17", BigDecimal(38.2120613), BigDecimal(128.598580)),
                        "밑반찬부터 생선조림까지 몽땅 맛있는 콩새식당",
                        "속초|가자미|가자미찜|생선조림",
                        BusinessHour(9, 18),
                        "SUNDAY",
                        "https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/f3d2b37e-7e95-4c80-9?alt=media"
                    ),
                    Store.Status.ACTIVE
                )
                em.persist(store)

                val gifticon = Gifticon(
                    1,
                    "gifticon 1",
                    90,
                    "",
                    BigDecimal(10000),
                    Gifticon.Category.CAFE,
                    Gifticon.Status.ACTIVE,
                    "https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media"
                )

                em.persist(gifticon)

                val gifticon2 = Gifticon(
                    1,
                    "gifticon 2",
                    30,
                    "",
                    BigDecimal(15000),
                    Gifticon.Category.CAFE,
                    Gifticon.Status.ACTIVE,
                    "https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/1306fbf0-e800-4105-a?alt=media"
                )

                em.persist(gifticon2)

                val touristSpot1 = TouristSpot(
                    "고래불관광지",
                    "관광지",
                    Location(
                        "경상북도 영덕군 병곡면 병곡리 72-8",
                        "경상북도 영덕군 병곡면 병곡리 72-8",
                        BigDecimal.valueOf(36.6003009541),
                        BigDecimal.valueOf(129.4105747991)
                    ),
                    880400,
                    Facility(
                        "관리사무소(2동)+주차장+화장실(6동)+샤워장(5동)+하계휴양소+취사동(4동)",
                        null,
                        "비치발리볼경기장+자전거대여점+풋살구장+체력단련시설",
                        "연수원",
                        "상가(2동)",
                        null,
                    ),
                    LocalDate.of(1988, 3, 28),
                    17100,
                    1000,
                    "명사20리 라고 불리는 8km에 이르는 넓고 긴 양질의 백사장과 빼어난 해송림 등 우수한 자연자원을 보유한 관광지",
                    "054-730-6513",
                    "경상북도 영덕군청",
                    LocalDate.of(2022, 8, 24)
                )
                em.persist(touristSpot1)

                val touristSpot2 = TouristSpot(
                    "장사해수욕장관광지",
                    "관광지",
                    Location(
                        "경상북도 영덕군 남정면 동해대로 3592",
                        "경상북도 영덕군 남정면 장사리 74-1",
                        BigDecimal.valueOf(36.2824187049),
                        BigDecimal.valueOf(129.3755938472)
                    ),
                    104219,
                    Facility(
                        "관리사무소+주차장+화장실(3동)+샤워장(2동)+광장",
                        "방갈로(20동)",
                        "미니축구장",
                        "전승기념마당",
                        "상가(2동)",
                        null,
                    ),
                    LocalDate.of(1981, 12, 31),
                    2500,
                    297,
                    "해수욕장의 청정 자연자원과 장사상륙작전이라는 역사적 사건을 보존한 관광지",
                    "054-730-6513",
                    "경상북도 영덕군청",
                    LocalDate.of(2022, 8, 24)
                )
                em.persist(touristSpot2)
            }
        }
    }
}