package com.example.nokbackend.domain.authentication

import com.example.nokbackend.infra.BaseEntity
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import javax.persistence.Entity

@Entity
class Authentication(
    val target: String,

    val code: String,

    val createDate: LocalDateTime = now(),

    val expireDate: LocalDateTime,

    val type: Type,

    var status: Status = Status.READY,

    id: Long = 0L
) : BaseEntity(id) {
    fun validCheck(code: String): Authentication {
        check(status == Status.READY) {"유효하지 않은 인증코드입니다."}
        check(now().isBefore(expireDate) ) { "인증코드의 유효기간이 만료되었습니다" }
        check(this.code === code) { "인증코드가 일치하지 않습니다."}
        return this
    }

    fun confirm(): Authentication {
        status = Status.AUTHENTICATED
        return this
    }

    fun expired(): Authentication {
        this.status = Status.EXPIRED
        return this
    }

    enum class Status { READY, EXPIRED, AUTHENTICATED }

    enum class Type { REGISTER, FIND_ID, FIND_PW }

}