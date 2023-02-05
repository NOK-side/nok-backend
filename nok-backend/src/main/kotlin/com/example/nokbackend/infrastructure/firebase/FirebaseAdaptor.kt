package com.example.nokbackend.infrastructure.firebase

import com.example.nokbackend.application.image.DeleteFileRequest
import com.example.nokbackend.domain.firebase.Firebase
import com.example.nokbackend.application.image.UploadFileResponse
import com.example.nokbackend.application.image.UploadFileRequest
import org.springframework.stereotype.Component

@Component
class FirebaseAdaptor(
    private val firebaseFileUploader: FirebaseFileUploader
): Firebase {
    override fun uploadFile(uploadFileRequests: List<UploadFileRequest>): List<UploadFileResponse> {
        return firebaseFileUploader.uploadFiles(uploadFileRequests)
    }

    override fun deleteFile(deleteFileRequests: List<DeleteFileRequest>) {
        firebaseFileUploader.deleteFiles(deleteFileRequests)
    }
}
