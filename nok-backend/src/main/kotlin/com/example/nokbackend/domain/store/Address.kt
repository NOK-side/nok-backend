package com.example.nokbackend.domain.store

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
data class Address(
    @Column
    val address: String
) {
}