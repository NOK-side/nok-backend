package com.example.nokbackend.domain.member

import com.example.nokbackend.AcceptanceTest
import com.example.nokbackend.DatabaseCleanup
import com.example.nokbackend.application.ConfirmAuthenticationRequest
import com.example.nokbackend.application.RegisterMemberRequest
import com.example.nokbackend.application.UpdateMemberRequest
import com.example.nokbackend.application.VerifyEmailRequest
import com.example.nokbackend.domain.authentication.Authentication
import com.fasterxml.jackson.databind.SerializationFeature
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import javax.persistence.EntityManager


class MemberAcceptanceTest @Autowired constructor(
    private val entityManager: EntityManager,
    databaseCleanup: DatabaseCleanup
) : AcceptanceTest(databaseCleanup) {

    @Test
    @DisplayName("회원 관리 테스트")
    fun memberProcessTest() {
        //when
        val verifyEmailRequest = AuthenticationFixture.verifyEmailRequest

        //then
        val verifyEmailResponse = 이메일_검증코드_전송_요청(verifyEmailRequest)
        이메일_검증코드_전송_요청됨(verifyEmailResponse)


        //when
        val authentication = 등록된_인증코드를_디비에서_가져옴(verifyEmailResponse)
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
//        회원_생성됨(registerMemberResponse)

        val resource = ClassPathResource("testImage.png")
        val file = resource.file

        val mockMultipartFile: MultipartFile = MockMultipartFile("testImage.png", FileInputStream(file))

//        val updateMemberRequest = UpdateMemberRequest("test", mockMultipartFile, Password("1q2w3e4r"))
//        회원정보_수정을_요청(updateMemberRequest)
    }

    fun 이메일_검증코드_전송_요청(verifyEmailRequest: VerifyEmailRequest): ExtractableResponse<Response> {


        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(verifyEmailRequest)
            .`when`()
            .post("/member/send/authentication/email")
            .then()
            .log().all()
            .extract()
    }

    fun 이메일_검증코드_전송_요청됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
    }

    fun 등록된_인증코드를_디비에서_가져옴(verifyEmailResponse: ExtractableResponse<Response>): Authentication {
        return entityManager.find(
            Authentication::class.java,
            1L
        )
    }

    fun 이메일_검증_요청(confirmAuthenticationRequest: ConfirmAuthenticationRequest): ExtractableResponse<Response> {
        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(confirmAuthenticationRequest)
            .`when`()
            .post("/member/verify/email")
            .then()
            .log().all()
            .extract()
    }

    fun 이메일_검증됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
    }

    fun 회원_생성을_요청(registerMemberRequest: RegisterMemberRequest): Response? {
        return Given {
            accept(ContentType.ANY)
            body(registerMemberRequest)
            log().all()
        } When {
            post("/member/register")
        } Then {
            log().all()
        } Extract {
            response()
        }

//        return RestAssured.given().log().all()
//            .contentType(MediaType.APPLICATION_JSON_VALUE)
//            .body(registerMemberRequest)
//            .`when`()
//            .post("/member/register")
//            .then()
//            .log().all()
//            .extract()
    }

    fun 회원_생성됨(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
    }

    fun 회원정보_수정을_요청(updateMemberRequest: UpdateMemberRequest): ExtractableResponse<Response> {
        val result = Given {
            accept(ContentType.ANY)
            body(updateMemberRequest)
            log().all()
        } When {
            put("/member/me/info")
        } Then {
            log().all()
        } Extract {
            response()
        }

        println(result)

        return RestAssured.given().log().all()
            .contentType(MediaType.ALL_VALUE)
            .body(updateMemberRequest)
            .`when`()
            .put("/member/me/info")
            .then()
            .log().all()
            .extract()
    }
}