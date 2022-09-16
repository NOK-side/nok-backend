package com.example.nokbackend.acceptance.member

import com.example.nokbackend.AcceptanceTest
import com.example.nokbackend.DatabaseCleanup
import com.example.nokbackend.application.ConfirmAuthenticationRequest
import com.example.nokbackend.application.RegisterMemberRequest
import com.example.nokbackend.application.VerifyEmailRequest
import com.example.nokbackend.domain.model.authentication.Authentication
import com.example.nokbackend.domain.model.member.Member
import com.example.nokbackend.fixture.*
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import javax.persistence.EntityManager


class MemberAcceptanceTest @Autowired constructor(
    private val entityManager: EntityManager,
    databaseCleanup: DatabaseCleanup
) : AcceptanceTest(databaseCleanup) {

//    @Test
    @DisplayName("회원 관리 테스트")
    fun memberProcessTest() {
        //when
        val verifyEmailRequest =  VerifyEmailRequest(email, Authentication.Type.REGISTER)

        //then
        이메일_검증코드_전송_요청_성공(verifyEmailRequest)

        //when
        val authentication = 등록된_인증코드를_디비에서_가져옴()
        val confirmAuthenticationRequest = ConfirmAuthenticationRequest(1L, email, authentication.code)

        //then
        이메일_검증_요청_성공(confirmAuthenticationRequest)

        //when
        val registerMemberRequest = RegisterMemberRequest(
            memberId = memberId,
            email = email,
            name = name,
            phoneNumber = phoneNumber,
            profileImage = "",
            password = password,
            role = Member.Role.USER,
            authenticationId = 1L,
            authenticationCode = authentication.code
        )

        //then
        회원_생성을_요청_성공(registerMemberRequest)
    }

    fun 이메일_검증코드_전송_요청_성공(verifyEmailRequest: VerifyEmailRequest): Response {
        return Given {
            body(verifyEmailRequest)
            contentType(MediaType.APPLICATION_JSON_VALUE)
            log().all()
        } When {
            post("/member/send/authentication/email")
        } Then {
            statusCode(HttpStatus.OK.value())
            log().all()
        } Extract {
            response()
        }
    }

    fun 등록된_인증코드를_디비에서_가져옴(): Authentication {
        return entityManager.find(
            Authentication::class.java,
            1L
        )
    }

    fun 이메일_검증_요청_성공(confirmAuthenticationRequest: ConfirmAuthenticationRequest): Response {
        return Given {
            body(confirmAuthenticationRequest)
            contentType(MediaType.APPLICATION_JSON_VALUE)
            log().all()
        } When {
            post("/member/verify/email")
        } Then {
            statusCode(HttpStatus.OK.value())
            log().all()
        } Extract {
            response()
        }
    }

    fun 회원_생성을_요청_성공(registerMemberRequest: RegisterMemberRequest): Response {
        return Given {
            body(registerMemberRequest)
            contentType(MediaType.APPLICATION_JSON_VALUE)
            log().all()
        } When {
            post("/member/register")
        } Then {
            statusCode(HttpStatus.CREATED.value())
            log().all()
        } Extract {
            response()
        }
    }

}