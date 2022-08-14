package com.example.nokbackend.domain.memberGifticon

import com.example.nokbackend.infra.BaseEntity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDate
import javax.persistence.Entity

@Entity
class MemberGifticon(
    val ownerId: Long,

    val gifticonId: Long,

    var dueDate: LocalDate,

    var status: Status = Status.ACTIVE,

    @CreatedDate
    var createDate: LocalDate = LocalDate.now(),

    @LastModifiedDate
    var modifiedDate: LocalDate = LocalDate.now(),

    id: Long = 0L
) : BaseEntity(id) {

    enum class Status { ACTIVE, USED, TRANSFERRED }
}