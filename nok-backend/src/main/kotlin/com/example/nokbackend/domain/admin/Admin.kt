package com.example.nokbackend.domain.admin

import com.example.nokbackend.domain.BaseEntity
import com.example.nokbackend.domain.member.LoginInformation
import com.example.nokbackend.domain.member.Password
import javax.persistence.AttributeOverride
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity

@Entity
class Admin (

    @Embedded
    var loginInformation: LoginInformation,

    @Embedded
    var adminInfomation: AdminInformation,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "password", nullable = false))
    var password: Password,

    id: Long = 0L
) : BaseEntity(id) {
    val adminId : String
        get() = adminInfomation.adminId

    val email : String
        get() = adminInfomation.email

    val name : String
        get() = adminInfomation.name

    val phoneNumber : String
        get() = adminInfomation.phoneNumber

    val division : String
        get() = adminInfomation.division
}