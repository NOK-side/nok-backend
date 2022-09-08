package com.example.nokbackend.infrastructure.mail

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class JavaMail(
    private val javaMailSender: JavaMailSender
) {

    fun send(email: String, subject: String, message: String) {
        val simpleMailMessage = SimpleMailMessage()
        simpleMailMessage.setSubject(subject)
        simpleMailMessage.setText(message)
        simpleMailMessage.setTo(email)
        javaMailSender.send(simpleMailMessage)
    }
}