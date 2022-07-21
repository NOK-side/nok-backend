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
    var name: String,

    @Column
    var phoneNumber: String,
) {
    fun update(updateMemberRequest: UpdateMemberRequest) {
        name = updateMemberRequest.name
        phoneNumber = updateMemberRequest.phoneNumber
    }
}