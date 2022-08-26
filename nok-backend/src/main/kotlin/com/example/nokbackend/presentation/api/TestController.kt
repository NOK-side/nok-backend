package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.MailSendInfo
import com.example.nokbackend.application.MailService
import com.example.nokbackend.application.ImageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/test")
@RestController
class TestController {

    @Autowired
    lateinit var mailService: MailService

    @Autowired
    lateinit var imageService: ImageService

    @GetMapping("/sendMail")
    fun sendMail(): ResponseEntity<String> {
        println("mailService")
        mailService.sendMail(MailSendInfo("rkdals213@naver.com", "subject", "message"));
        return ResponseEntity.ok("good");
    }

    @PostMapping("/sendImage")
    fun sendEmail(@RequestParam multipartFile: MultipartFile): ResponseEntity<String> {
        val blob = imageService.uploadFile(multipartFile, "test")
        return ResponseEntity.ok(blob)
    }

}