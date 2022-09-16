package com.example.nokbackend.application

import com.example.nokbackend.domain.model.firebase.Firebase
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class ImageService(
    private val uuidGenerator: UUIDGenerator,
    private val firebase: Firebase
) {

    fun uploadFiles(multipartFiles: List<MultipartFile>): List<UploadFileResponse> {
        val uploadFileRequests = multipartFiles.map {
            UploadFileRequest(uuidGenerator.generate(20), it)
        }

        return firebase.uploadFile(uploadFileRequests)
    }
}
