package com.example.nokbackend.domain.member

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class MemberInformation(
    @Column
    val memberId: String,

    @Column
    val email: String,

    @Column
    var name: String,

    @Column
    var phoneNumber: String,
) {
}