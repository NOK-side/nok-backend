package com.example.nokbackend.domain.authentication

import com.example.nokbackend.infra.BaseEntity
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.Entity

@Entity
class Authentication(
    val targetId: Long,

    val key: String,

    @CreatedDate
    val createDate: LocalDateTime,

    val expireDate: LocalDateTime,

    id: Long = 0L
) : BaseEntity(id) {

    enum class Status { READY, AUTHENTICATED }
}