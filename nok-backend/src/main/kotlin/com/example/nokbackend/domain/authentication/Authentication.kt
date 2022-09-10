package com.example.nokbackend.domain.authentication

import com.example.nokbackend.domain.BaseEntity
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Authentication(
    val target: String,

    val code: String,

    val createDate: LocalDateTime = now(),

    var expireDate: LocalDateTime,

    @Enumerated(value = EnumType.STRING)
    val type: Type,

    @Enumerated(value = EnumType.STRING)
    var status: Status = Status.READY,

    id: Long = 0L
) : BaseEntity(id) {
    fun validate(code: String) {
        check(status == Status.READY) { "유효하지 않은 인증코드입니다." }
        check(now().isBefore(expireDate)) { "인증코드의 유효기간이 만료되었습니다" }
        verifyCode(code)
    }

    fun confirm() {
        status = Status.AUTHENTICATED
    }

    fun verifyCode(code: String) {
        require(this.code == code) { "인증코드가 일치하지 않습니다." }
    }

    fun expired() {
        this.status = Status.EXPIRED
    }

    enum class Status { READY, EXPIRED, AUTHENTICATED }

    enum class Type { REGISTER, FIND_ID, FIND_PW, WITHDRAW }

}