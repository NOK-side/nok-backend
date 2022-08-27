package com.example.nokbackend.domain.memberGifticon

import com.example.nokbackend.domain.member.Member
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

    fun transferGifticonTo(targetId: Long, targetDueDate: LocalDate): MemberGifticon {
        status = Status.TRANSFERRED

        return MemberGifticon(
            ownerId = targetId,
            dueDate = targetDueDate,
            gifticonId = gifticonId,
            status = Status.ACTIVE
        )
    }

    fun use() {
        status = Status.USED
    }

    fun checkOwner(member: Member) {
        require(ownerId == member.id)
    }

    enum class Status { ACTIVE, USED, TRANSFERRED }
}