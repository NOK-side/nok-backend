package com.example.nokbackend.domain

import javax.persistence.*

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    // todo : 암호화 필요
    var password: String,

    val email: String,

    var name: String,

    @Enumerated(value = EnumType.STRING)
    var role: Role,

    var phoneNumber: String,

    @Enumerated(value = EnumType.STRING)
    var status: Status
) {
    enum class Role { USER, STORE, ADMIN }
    enum class Status { READY, ACTIVE, DELETED }

    companion object {
        val DUMMY: Member
            get() = Member(-1, "", "", "",  Role.USER, "", Status.READY)
    }
}

