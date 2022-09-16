package com.example.nokbackend.domain.model.member

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class MemberInformation(
    @Column(unique = true)
    val memberId: String,

    @Column(unique = true)
    val email: String,

    @Column
    val name: String,

    @Column
    val phoneNumber: String,

    @Column
    val profileImage: String,
)
