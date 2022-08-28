package com.example.nokbackend.domain.cart

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import java.lang.RuntimeException

fun CartRepository.findByIdCheck(id: Long): Cart = findByIdOrNull(id) ?: throw RuntimeException("카트가 존재하지 않습니다")

interface CartRepository : JpaRepository<Cart, Long> {

    fun findByOwnerId(ownerId: Long): List<Cart>
}