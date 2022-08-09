package com.example.nokbackend.domain.member

import com.example.nokbackend.AcceptanceTest
import com.example.nokbackend.application.ConfirmAuthenticationRequest
import com.example.nokbackend.application.RegisterMemberRequest
import com.example.nokbackend.application.VerifyEmailRequest
import com.example.nokbackend.domain.authentication.Authentication
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import javax.persistence.EntityManager


class MemberAcceptanceTes @Autowired constructor(
    private val entityManager: EntityManager
) : AcceptanceTest() {

    // todo : 이메일 전송부분 mocking 필요
    @Test
    @DisplayName("회원 가입 프로세스 테스트")
    fun memberProcessTest() {
        //when
        val verifyEmailRequest = AuthenticationFixture.verifyEmailRequest

        //then
        val verifyEmailResponse = 이메일_검증코드_전송_요청(verifyEmailRequest)
        이메일_검증코드_전송_요청됨(verifyEmailResponse)


        //when
        val authentication = entityManager.find(Authentication::class.java, 1L)
        val confirmAuthenticationRequest = AuthenticationFixture.confirmAuthenticationRequest
            .copy(code = authentication.code)

        //then
        val confirmAuthenticationResponse = 이메일_검증_요청(confirmAuthenticationRequest)
        이메일_검증됨(confirmAuthenticationResponse)


        //when
        val registerMemberRequest = MemberFixture.memberRequest
            .copy(authenticationCode = authentication.code)

        //then
        val registerMemberResponse = 회원_생성을_요청(registerMemberRequest)
        회원_생성됨(registerMemberResponse)
    }

    fun 이메일_검증코드_전송_요청(verifyEmailRequest: VerifyEmailRequest): ExtractableResponse<Response> {
        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(verifyEmailRequest)
            .`when`()
            .post("/authentication/send/email")
            .then()
            .log().all()
            .extract()
    }

    fun 이메일_검증_요청(confirmAuthenticationRequest: ConfirmAuthenticationRequest): ExtractableResponse<Response> {
        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(confirmAuthenticationRequest)
            .`when`()
            .post("/authentication/verify/email")
            .then()
            .log().all()
            .extract()
    }

    fun 회원_생성을_요청(registerMemberRequest: RegisterMemberRequest): ExtractableResponse<Response> {
        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(registerMemberRequest)
            .`when`()
            .post("/member/register")
            .then()
            .log().all()
            .extract()
    }

    fun 회원_생성됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
    }

    fun 이메일_검증코드_전송_요청됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
    }

    fun 이메일_검증됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
    }
}