package com.example.nokbackend.domain.mail

interface Mail {

    fun send(target: String, subject: String, content: String, )
}