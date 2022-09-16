package com.example.nokbackend.domain.model.mail

interface Mail {

    fun send(target: String, subject: String, content: String, )
}