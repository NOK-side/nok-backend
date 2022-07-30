package com.example.nokbackend.domain.store

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Address(
    @Column
    val address: String
)

// todo : 지도 api가 제공해주는 주소양식과 좌표같은거 주는지 확인하고 데이터 매핑하기