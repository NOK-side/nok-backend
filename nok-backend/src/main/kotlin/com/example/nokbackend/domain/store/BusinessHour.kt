package com.example.nokbackend.domain.store

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class BusinessHour(
    @Column
    var fromHour: Int,

    @Column
    var toHour: Int
)