package com.example.nokbackend.security

@Target(AnnotationTarget.TYPE, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Authenticated

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class MemberClaim

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class HeaderClaim(val value: String = "")