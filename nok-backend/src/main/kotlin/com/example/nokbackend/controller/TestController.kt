package com.example.nokbackend.controller

import com.example.nokbackend.util.MailSendInfo
import com.example.nokbackend.util.MailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/test")
@RestController
class TestController {

    @Autowired
    lateinit var mailService: MailService;

    @GetMapping("/sendMail")
    fun sendMail(): ResponseEntity<String> {
        println("mailService")
        mailService.sendMail(MailSendInfo("dae4805@naver.com", "subject", "message"));
        return ResponseEntity.ok("good");
    }

}