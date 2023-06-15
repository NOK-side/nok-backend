package com.example.nokbackend.domain.order

import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Orders, Long> {
}