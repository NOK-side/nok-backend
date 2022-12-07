package com.example.nokbackend.domain.admin

import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository <Admin, Long> {

}