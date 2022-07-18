package com.example.nokbackend.application

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class MailService(
    private val mailSender: JavaMailSender
) {

    fun sendMail(mailSendInfo: MailSendInfo) {
        val email = SimpleMailMessage()
        email.setSubject(mailSendInfo.subject)
        email.setText(mailSendInfo.message)
        email.setTo(mailSendInfo.email)
        mailSender.send(email);
    }

}

data class MailSendInfo(
    val email: String,
    val subject: String,
    val message: String
)