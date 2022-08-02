package com.example.nokbackend

import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.Password
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
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
            }
        }
    }
}