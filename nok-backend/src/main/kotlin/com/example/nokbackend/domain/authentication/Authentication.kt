package com.example.nokbackend.domain.authentication

import com.example.nokbackend.infra.BaseEntity
import org.springframework.data.annotation.CreatedDate
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

    fun checkExpiration() {
        check(now() in createDate..expireDate) { "인증코드의 유효기간이 만료되었습니다" }
    }

    fun checkCode(code: String) {
        require(this.code == code) { "인증번호가 일치하지 않습니다." }
    }

    fun confirm() {
        status = Status.AUTHENTICATED
    }

    fun checkAuthenticated() {
        check(status == Status.AUTHENTICATED) { "인증완료되지 않은 코드입니다" }
    }

    fun expired(): Authentication {
        this.status = Status.EXPIRED
        return this
    }


    enum class Status { READY, EXPIRED, AUTHENTICATED }

    enum class Type { JOIN, FIND_ID, FIND_PW }

}