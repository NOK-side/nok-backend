package com.example.nokbackend.infra.mail

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

    private fun createRandomString(length: Int): String {
        val charList: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return (6..length)
            .map { kotlin.random.Random.nextInt(0, charList.size) }
            .map(charList::get)
            .joinToString("")
    }
}

data class MailSendInfo(
    val email: String,
    val subject: String,
    val message: String
)