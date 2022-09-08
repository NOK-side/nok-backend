package com.example.nokbackend.infrastructure.mail

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

@Aspect
class MailAspect {

    @Around("execution(* com.example.nokbackend.infrastructure.mail.MailAdaptor.*(..))")
    fun execute(joinPoint: ProceedingJoinPoint): Any? {
        val message = joinPoint.signature.toShortString()
        println(message)
        return joinPoint.proceed()
    }
}