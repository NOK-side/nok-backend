package com.example.nokbackend.domain.memberGifticon

import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.domain.member.Member
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

    fun validate(member: Member) {
        require(ownerId == member.id) { "본인이 소유한 기프티콘이 아닙니다" }
        check(status == Status.ACTIVE) { "${status}한 기프티콘입니다" }
        check(dueDate >= LocalDate.now()) { "사용기간이 만료되었습니다" }
    }

    fun use() {
        status = Status.USED
    }

    enum class Status { ACTIVE, USED, TRANSFERRED }
}