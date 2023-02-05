//package com.example.nokbackend.domain.authentication
//
//import com.example.nokbackend.application.authentication.AuthenticationService
//import com.example.nokbackend.application.authentication.ConfirmAuthenticationRequest
//import com.example.nokbackend.application.util.UUIDGenerator
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Assertions.fail
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertAll
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.test.context.ActiveProfiles
//import java.time.LocalDateTime
//
//@SpringBootTest
//@ActiveProfiles("test")
//class AuthenticationServiceTest @Autowired constructor(
//    val authService: AuthenticationService,
//    val authRepo: AuthenticationRepository,
//    val uuidGenerator: UUIDGenerator
//) {
//
//    val authTarget = "dae4805@naver.com"
//    val authType = Authentication.Type.REGISTER
//
//    @Test
//    fun `인증코드 생성 함수 테스트`() {
//        val randomString = uuidGenerator.generate(10)
//        println("randomString = $randomString")
//        assertEquals(randomString.length, 10)
//    }
//
//    @Test
//    fun `인증코드 생성`() {
//        val authentication = authRepo.findById(authService.registerAuthentication(authTarget, authType).id).get()
//
//        assertAll(
//            { assertEquals(authentication.type, authType) },
//            { assertEquals(authentication.target, authTarget) }
//        )
//    }
//
//    @Test
//    fun `인증코드 확인 - 정상 입력`() {
//        val authentication = authRepo.findById(authService.registerAuthentication(authTarget, authType).id).get()
//
//        val id = authentication.id
//        val code = authentication.code
//
//        authService.confirmAuthentication(ConfirmAuthenticationRequest(id, authTarget, code), authType)
//    }
//
//    @Test
//    fun `인증코드 확인 - 인증코드 잘못 입력`() {
//        val authentication = authRepo.findById(authService.registerAuthentication(authTarget, authType).id).get()
//
//        val id = authentication.id
//        val code = "쿠쿠루삥뽕"
//
//        try {
//            println("code = ${authentication.code}, input = $code")
//            authService.confirmAuthentication(ConfirmAuthenticationRequest(id, authTarget, code), authType)
//            fail()
//        } catch (exception: IllegalArgumentException) {
//            //pass
//        }
//    }
//
//    @Test
//    fun `인증코드 확인 - 인증코드 기한 초과`() {
//        val authentication = authRepo.findById(authService.registerAuthentication(authTarget, authType).id).get()
//
//        val id = authentication.id
//        val code = authentication.code
//
//        authentication.expireDate = LocalDateTime.now().minusDays(1)
//        authRepo.save(authentication)
//
//
//        try {
//            println("expireDate = ${authentication.expireDate}, input = ${LocalDateTime.now()}")
//            authService.confirmAuthentication(ConfirmAuthenticationRequest(id, authTarget, code), authType)
//            fail()
//        } catch (exception: IllegalStateException) {
//            //pass
//        }
//    }
//
//    @Test
//    fun `인증코드 확인 - 만료된 인증 코드`() {
//        val authentication = authRepo.findById(authService.registerAuthentication(authTarget, authType).id).get()
//
//        val id = authentication.id
//        val code = authentication.code
//
//        authentication.status = Authentication.Status.EXPIRED
//        authRepo.save(authentication)
//
//        try {
//            println("status = ${authentication.status}")
//            authService.confirmAuthentication(ConfirmAuthenticationRequest(id, authTarget, code), authType)
//            fail()
//        } catch (exception: IllegalStateException) {
//            //pass
//        }
//    }
//
//    @Test
//    fun `인증코드 사용`() {
//        val before = authRepo.findById(authService.registerAuthentication(authTarget, authType).id).get()
//        val beforeStatus = before.status
//        authService.confirmAuthentication(ConfirmAuthenticationRequest(before.id, authTarget, before.code), authType)
//        val afterStatus = authRepo.findById(before.id).get().status
//
//        assertAll(
//            { assertEquals(beforeStatus, Authentication.Status.READY) },
//            { assertEquals(afterStatus, Authentication.Status.AUTHENTICATED) }
//        )
//    }
//
//}
//
