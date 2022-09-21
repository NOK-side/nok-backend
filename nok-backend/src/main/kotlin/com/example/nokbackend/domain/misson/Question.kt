package com.example.nokbackend.domain.misson

import com.example.nokbackend.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Question(
    @ManyToOne
    val questionGroup: QuestionGroup,

    var question: String,

    var answer: Int,

    id: Long = 0L
) : BaseEntity(id) {

    fun mark(answer: Int): Boolean {
        return this.answer == answer
    }
}

