package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.AuthenticationService
import com.example.nokbackend.application.ConfirmAuthenticationRequest
import com.example.nokbackend.application.VerifyEmailRequest
import com.example.nokbackend.domain.authentication.Authentication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/authentication")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/send/email")
    fun sendAuthenticationToEmail(@Valid @RequestBody verifyEmailRequest: VerifyEmailRequest): ResponseEntity<Any> {
        val authenticationResponse = authenticationService.sendAuthenticationToEmail(verifyEmailRequest.email, Authentication.Type.REGISTER)
        return ResponseEntity.ok().body(ApiResponse.success(authenticationResponse))
    }

    @PostMapping("/verify/email")
    fun verifyAuthenticationCodeForEmail(@RequestBody confirmAuthenticationRequest: ConfirmAuthenticationRequest): ResponseEntity<Any> {
        authenticationService.confirmAuthentication(confirmAuthenticationRequest, Authentication.Type.REGISTER)
        return ResponseEntity.ok().build()
    }

}