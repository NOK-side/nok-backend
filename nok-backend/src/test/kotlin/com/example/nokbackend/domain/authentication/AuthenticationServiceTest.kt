package com.example.nokbackend.domain.authentication

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AuthenticationServiceTest @Autowired constructor(
    val authService: AuthenticationService
) {

    @Test
    fun `인증코드 생성 함수 테스트`(){
        val randomString = createRandomString(10)
        println("randomString = $randomString")
        assertThat(randomString.length).isEqualTo(10)
    }

    @Test
    fun `인증코드 생성`() {
        val authentication = authService.create("dae4805@naver.com", Authentication.Type.REGISTER)
    }

}

