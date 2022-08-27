package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.ImageService
import com.example.nokbackend.application.MailEvent
import com.example.nokbackend.application.MailEventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/test")
@RestController
class TestController {

    @Autowired
    lateinit var mailEventHandler: MailEventHandler

    @Autowired
    lateinit var imageService: ImageService

    @Autowired
    lateinit var applicationEventPublisher: ApplicationEventPublisher


    @GetMapping("/sendMail")
    fun sendMail(): ResponseEntity<String> {
        applicationEventPublisher.publishEvent(MailEvent("rkdals213@naver.com", "이메일 검증", "message"))
        return ResponseEntity.ok("good");
    }

    @PostMapping("/sendImage")
    fun sendEmail(@RequestParam multipartFile: MultipartFile): ResponseEntity<String> {
        val blob = imageService.uploadFile(multipartFile, "test")
        return ResponseEntity.ok(blob)
    }

}