package com.example.nokbackend

import com.example.nokbackend.domain.gifticon.Gifticon
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.Password
import com.example.nokbackend.domain.store.Address
import com.example.nokbackend.domain.store.BusinessHour
import com.example.nokbackend.domain.store.Store
import com.example.nokbackend.domain.store.StoreInformation
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableJpaAuditing
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
                    memberId = "rkdals213",
                    email = "rkdals213@naver.com",
                    password = Password("1q2w3e4r"),
                    name = "tester",
                    role = Member.Role.USER,
                    phoneNumber = "01000000000",
                    profileImg = "testImg",
                    status = Member.Status.ACTIVE
                )
                em.persist(member)

                val member2 = Member(
                    memberId = "rkdals2134",
                    email = "rkdals2134@naver.com",
                    password = Password("1q2w3e4r"),
                    name = "tester",
                    role = Member.Role.STORE,
                    phoneNumber = "01000000000",
                    profileImg = "testImg",
                    status = Member.Status.ACTIVE
                )
                em.persist(member2)

                val store = Store(
                    1,
                    StoreInformation(
                        "123123123",
                        "test store",
                        StoreInformation.Category.CAFE,
                        "000-0000-0000",
                        Address(""),
                        "",
                        "",
                        "",
                        BusinessHour(9, 18),
                        "SUNDAY"
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
                    Gifticon.Status.ACTIVE
                )

                em.persist(gifticon)

                val gifticon2 = Gifticon(
                    1,
                    "gifticon 2",
                    30,
                    "",
                    BigDecimal(15000),
                    Gifticon.Category.CAFE,
                    Gifticon.Status.ACTIVE
                )

                em.persist(gifticon2)
            }
        }
    }
}