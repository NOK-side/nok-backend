package com.example.nokbackend.domain.member

import com.example.nokbackend.application.UpdateMemberRequest
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class MemberInformation(
    @Column
    val memberId: String,

    @Column
    val email: String,

    @Column
    val name: String,

    @Column
    val phoneNumber: String,

    @Column
    val profileImage: String,
)
