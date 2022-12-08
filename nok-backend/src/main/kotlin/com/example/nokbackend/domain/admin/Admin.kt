package com.example.nokbackend.domain.admin

import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.domain.member.LoginInformation
import com.example.nokbackend.domain.member.Password
import javax.persistence.*

@Entity
@Table(name = "admin_members")
class Admin (

    @Embedded
    var loginInformation: LoginInformation,

    @Embedded
    var adminInformation: AdminInformation,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "password", nullable = false))
    var password: Password,

    id: Long = 0L
) : BaseEntity(id) {
    val adminId : String
        get() = adminInformation.adminId

    val email : String
        get() = adminInformation.email

    val name : String
        get() = adminInformation.name

    val phoneNumber : String
        get() = adminInformation.phoneNumber

    val division : String
        get() = adminInformation.division

    val profileImage : String
        get() = adminInformation.profileImage
}