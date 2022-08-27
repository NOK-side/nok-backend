package com.example.nokbackend.application

import org.springframework.context.event.EventListener
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class MailEventHandler(
    private val mailSender: JavaMailSender
) {

    @Async
    @EventListener
    fun listenMailEvent(mailEvent: MailEvent) {
        sendEmail(mailEvent.email, mailEvent.subject, mailEvent.message)
    }

    private fun sendEmail(email: String, subject: String, message: String) {
        val simpleMailMessage = SimpleMailMessage()
        simpleMailMessage.setSubject(subject)
        simpleMailMessage.setText(message)
        simpleMailMessage.setTo(email)
        mailSender.send(simpleMailMessage)
    }
}

data class MailEvent(
    val email: String,
    val subject: String,
    val message: String
)
