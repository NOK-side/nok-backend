package com.example.nokbackend.presentation.api

import com.example.nokbackend.application.image.DeleteFileRequest
import com.example.nokbackend.application.image.ImageService
import com.example.nokbackend.application.image.UploadFileResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/common")
class CommonController(
    private val imageService: ImageService,
) {
    @PostMapping("/upload/img")
    fun uploadImage(@RequestParam images: List<MultipartFile>): ResponseEntity<ApiResponse<List<UploadFileResponse>>> {
        val response = imageService.uploadFiles(images)
        return responseEntity {
            body = apiResponse {
                data = response
            }
        }
    }

    @PostMapping("/delete/img")
    fun deleteImage(@RequestBody deleteFileRequests: List<DeleteFileRequest>): ResponseEntity<ApiResponse<EmptyBody>> {
        imageService.deleteFiles(deleteFileRequests)
        return responseEntity {
            body = apiResponse {
                data = EmptyBody
            }
        }
    }
}