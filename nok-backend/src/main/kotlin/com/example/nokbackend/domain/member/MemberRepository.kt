package com.example.nokbackend.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

fun MemberRepository.findByEmail(email: String): Member? = findByInformationEmail(email)
fun MemberRepository.findByEmailCheck(email: String): Member = findByInformationEmail(email) ?: throw RuntimeException("해당 Email로 등록된 회원이 없습니다")
fun MemberRepository.findByMemberIdCheck(memberId: String): Member = findByInformationMemberId(memberId) ?: throw RuntimeException("해당 ID로 등록된 회원이 없습니다")
fun MemberRepository.existByMemberId(memberId: String): Boolean = existsByInformationMemberId(memberId)
fun MemberRepository.existByEmail(memberId: String): Boolean = existsByInformationEmail(memberId)
fun MemberRepository.findByIdCheck(id: Long): Member = findByIdOrNull(id) ?: throw RuntimeException("회원이 존재하지 않습니다")
fun MemberRepository.findByNameAndPhoneNumber(name: String, phoneNumber: String) = findByInformationNameAndInformationPhoneNumber(name, phoneNumber) ?: throw RuntimeException("해당정보에 맞는 회원이 존재하지 않습니다")

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByInformationEmail(email: String): Member?

    fun findByInformationMemberId(memberId: String): Member?

    fun existsByInformationMemberId(memberId: String): Boolean

    fun existsByInformationEmail(memberId: String): Boolean

    fun findByInformationNameAndInformationPhoneNumber(name: String, phoneNumber: String): Member?

}