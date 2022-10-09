package com.example.nokbackend.security

import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.member.findByEmail
import com.example.nokbackend.util.http.BearerHeader
import com.example.nokbackend.util.http.HeaderHandler
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class JwtSessionArgumentResolver(
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(MemberClaim::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val authorization = webRequest.getHeader(HttpHeaders.AUTHORIZATION) ?: return Member.DUMMY

        val token = HeaderHandler.extractBearerToken(authorization)

        val email = jwtTokenProvider.getEmail(token)

        return memberRepository.findByEmail(email) ?: Member.DUMMY
    }
}