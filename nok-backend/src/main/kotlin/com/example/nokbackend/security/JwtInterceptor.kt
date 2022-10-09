package com.example.nokbackend.security

import com.example.nokbackend.util.http.HeaderHandler
import org.springframework.http.HttpHeaders
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtInterceptor(private val jwtTokenProvider: JwtTokenProvider) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (handler !is HandlerMethod || !isAuthenticationPresent(handler)) {
            return true
        }

        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION) ?: throw IllegalAccessException("토큰이 존재하지 않습니다")

        val token = HeaderHandler.extractBearerToken(authorization)

        val validToken = jwtTokenProvider.isValidToken(token)

        if (!validToken) {
            throw IllegalAccessException("유효한 토큰이 아닙니다")
        }

        return true
    }

    private fun isAuthenticationPresent(handler: HandlerMethod): Boolean {
        return (handler.hasMethodAnnotation(Authenticated::class.java)
                || handler.beanType.isAnnotationPresent(Authenticated::class.java))
    }
}