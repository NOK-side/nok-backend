package com.example.nokbackend.domain.model.store

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class BusinessHour(
    @Column
    val fromHour: Int,

    @Column
    val toHour: Int
) {
    init {
        require(fromHour in 0..24) { "영업 시작 시간으로 설정할 수 없는 범위입니다" }
        require(toHour in 0..24) { "영업 종료 시간으로 설정할 수 없는 범위입니다" }
    }
}