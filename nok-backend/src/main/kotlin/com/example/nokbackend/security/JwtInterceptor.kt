package com.example.nokbackend.security

import com.example.nokbackend.util.http.BearerHeader
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val BEARER = "Bearer"

class JwtInterceptor(private val jwtTokenProvider: JwtTokenProvider) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (handler !is HandlerMethod || !isAuthenticationPresent(handler)) {
            return true
        }

        val token = extractBearerToken(request)

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

    private fun extractBearerToken(request: HttpServletRequest): String {
        val authorization = request.getHeader(AUTHORIZATION) ?: throw IllegalAccessException("토큰이 존재하지 않습니다")
        val (tokenType, token) = BearerHeader.splitToTokenFormat(authorization)

        if (tokenType != BEARER) {
            throw IllegalAccessException("Bearer 형식의 토큰이 아닙니다")
        }

        return token
    }
}