package com.example.nokbackend.domain.member

import com.example.nokbackend.application.member.UpdateMemberRequest
import com.example.nokbackend.application.member.UpdatePasswordRequest
import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.exception.UnidentifiedUserException
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@DynamicUpdate
@Table(name = "members")
class Member(
    @Embedded
    var information: MemberInformation,

    @Embedded
    var loginInformation: LoginInformation,

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

    val profileImage: String
        get() = information.profileImage

    fun authenticate(password: Password) {
        identify(this.password == password) { "사용자 정보가 일치하지 않습니다." }
    }

    fun checkActivation() {
        identify(status == Status.ACTIVE) { "활성화된 회원이 아닙니다 ($status)" }
    }

    fun activate() {
        status = Status.ACTIVE
    }

    fun inActivate() {
        status = Status.DELETED
    }

    fun update(updateMemberRequest: UpdateMemberRequest) {

        information = MemberInformation(
            memberId = memberId,
            email = email,
            name = updateMemberRequest.name ?: information.name,
            phoneNumber = updateMemberRequest.phoneNumber ?: information.phoneNumber,
            profileImage = information.profileImage
        )
    }

    fun newPassword(newPassword: Password) {
        password = newPassword
    }

    fun updatePassword(updatePasswordRequest: UpdatePasswordRequest) {
        identify(password == updatePasswordRequest.oldPassword) { "기존 비밀번호가 일치하지 않습니다" }
        password = updatePasswordRequest.newPassword
    }

    private fun identify(value: Boolean, lazyMessage: () -> Any = {}) {
        if (!value) {
            val message = lazyMessage()
            throw UnidentifiedUserException(message.toString())
        }
    }

    enum class Role { USER, STORE, ADMIN, NOTHING, }

    enum class Status { READY, ACTIVE, DELETED, }

    companion object {
        val DUMMY: Member
            get() = Member(MemberInformation("", "", "", "", ""), LoginInformation("", ""), Password(""), Role.USER, Status.ACTIVE)
    }
}

