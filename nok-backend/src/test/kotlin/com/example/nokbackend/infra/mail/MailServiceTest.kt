package com.example.nokbackend.infra.mail

import com.example.nokbackend.application.MailSendInfo
import com.example.nokbackend.application.MailService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MailServiceTest @Autowired constructor(
    private val mailService: MailService
) {

    @Test
    fun `메일이 진짜로 발송되나 함 볼까`() {
        mailService.sendMail(MailSendInfo("rkdals213@naver.com", "subject", "message"));
    }
}