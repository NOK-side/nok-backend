package com.example.nokbackend.domain.membermission

import com.example.nokbackend.domain.BaseEntity
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class ResultOfMemberMissionQuestion(

    @ManyToOne
    val memberMission: MemberMission,

    val fromId: String,

    val respondent: String,

    val score: Int,

    val results: String,

    @CreatedDate
    val createDate: LocalDate = LocalDate.now(),

    id: Long = 0
): BaseEntity(id){
}