package com.example.nokbackend.domain.authentication

import org.springframework.data.jpa.repository.JpaRepository

interface AuthenticationRepository : JpaRepository<Authentication, Long> {
}