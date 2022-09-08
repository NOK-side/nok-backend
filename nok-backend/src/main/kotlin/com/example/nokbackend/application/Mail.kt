package com.example.nokbackend.application

interface Mail {

    fun send(target: String, subject: String, content: String, )
}