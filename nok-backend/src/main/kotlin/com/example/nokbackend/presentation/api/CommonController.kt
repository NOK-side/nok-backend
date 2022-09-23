package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/common")
class CommonController(
    private val imageService: ImageService,
) {
    @PostMapping("/upload/img")
    fun uploadImage(@RequestParam images: List<MultipartFile>): ResponseEntity<Any> {
        val response = imageService.uploadFiles(images)
        return ResponseEntity.ok().body(ApiResponse.success(response))
    }

    @PostMapping("/delete/img")
    fun deleteImage(@RequestBody deleteFileRequests: List<DeleteFileRequest>): ResponseEntity<Any> {
        imageService.deleteFiles(deleteFileRequests)
        return ResponseEntity.ok().body(ApiResponse.success(EmptyBody))
    }
}