package com.example.nokbackend.infrastructure.mail

import com.example.nokbackend.domain.model.mail.Mail
import org.springframework.stereotype.Component

@Component
class MailAdaptor(
    private val javaMail: JavaMail
) : Mail {
    override fun send(target: String, subject: String, content: String) {
        javaMail.send(target, subject, content)
    }
}