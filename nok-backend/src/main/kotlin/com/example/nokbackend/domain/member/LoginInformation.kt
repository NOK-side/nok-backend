package com.example.nokbackend.domain.member

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class LoginInformation(
    @Column
    val userAgent: String,

    @Column
    val refreshToken: String,

    @Column
    val fcmCode: String
) {
}