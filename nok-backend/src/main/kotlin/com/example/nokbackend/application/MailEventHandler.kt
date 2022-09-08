package com.example.nokbackend.application

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class MailEventHandler(
    private val mail: Mail
) {

    @Async
    @EventListener
    fun listenMailEvent(mailEvent: MailEvent) {
        mail.send(mailEvent.email, mailEvent.subject, mailEvent.message)
    }
}

data class MailEvent(
    val email: String,
    val subject: String,
    val message: String
)
