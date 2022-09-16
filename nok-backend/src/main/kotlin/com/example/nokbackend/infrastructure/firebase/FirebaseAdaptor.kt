package com.example.nokbackend.infrastructure.firebase

import com.example.nokbackend.domain.model.firebase.Firebase
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
