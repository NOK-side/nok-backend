package com.example.nokbackend.infrastructure

import com.example.nokbackend.application.Firebase
import com.example.nokbackend.application.UploadFileResponse
import com.example.nokbackend.application.UploadFileRequest
import org.springframework.stereotype.Component

@Component
class FirebaseAdaptor(
    private val firebaseFileUploader: FirebaseFileUploader
): Firebase {
    override fun uploadFile(fileUploadFileRequests: List<UploadFileRequest>): List<UploadFileResponse> {
        return firebaseFileUploader.uploadFiles(fileUploadFileRequests)
    }
}
