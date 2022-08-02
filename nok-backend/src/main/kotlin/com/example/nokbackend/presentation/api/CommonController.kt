package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@RestController
@RequestMapping("/common")
class CommonController(
    private val imageService: ImageService,
) {
    @PostMapping("/upload/img")
    fun uploadImage(@RequestParam img: MultipartFile): ResponseEntity<String> {
        val uuid = UUID.randomUUID().toString()
        val blob = imageService.uploadFile(img, uuid)
        return ResponseEntity.ok(blob)
    }
}