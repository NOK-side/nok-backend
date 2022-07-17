package com.example.nokbackend.domain.member

import com.example.nokbackend.infra.BaseEntity
import javax.persistence.*

@Entity
class Member(
    @Embedded
    var information: MemberInformation,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "password", nullable = false))
    var password: Password,

    @Enumerated(value = EnumType.STRING)
    var role: Role,

    @Enumerated(value = EnumType.STRING)
    var status: Status,

    id: Long = 0L
) : BaseEntity(id) {
    val memberId: String
        get() = information.memberId

    val email: String
        get() = information.email

    val name: String
        get() = information.name

    val phoneNumber: String
        get() = information.phoneNumber

    constructor(
        memberId: String,
        email: String,
        name: String,
        phoneNumber: String,
        password: Password,
        role: Role = Role.NOTHING,
        status: Status = Status.READY
    ) : this(MemberInformation(memberId, email, name, phoneNumber), password, role, status)

    fun authenticate(password: Password) {
        check(this.password == password) { "사용자 정보가 일치하지 않습니다." }
    }

    fun checkActivation() {
        check(status == Status.ACTIVE) { "활성화된 회원이 아닙니다 ($status)" }
    }

    fun activate() {
        status = Status.ACTIVE
    }

    enum class Role { USER, STORE, ADMIN, NOTHING, }

    enum class Status { READY, ACTIVE, DELETED, }

    companion object {
        val DUMMY: Member
            get() = Member("", "", "", "", Password(""))
    }
}

