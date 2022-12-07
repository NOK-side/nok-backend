package com.example.nokbackend.domain.admin

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class AdminInformation (
    @Column(unique = true)
    val adminId: String,

    @Column(unique = true)
    val email: String,

    @Column
    val name: String,

    @Column
    val phoneNumber: String,

    @Column
    val division: String,

    @Column
    val profileImage: String,
)