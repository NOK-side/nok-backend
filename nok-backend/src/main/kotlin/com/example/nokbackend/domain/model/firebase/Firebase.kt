package com.example.nokbackend.domain.model.firebase

import com.example.nokbackend.application.UploadFileRequest
import com.example.nokbackend.application.UploadFileResponse

interface Firebase {

    fun uploadFile(fileUploadFileRequests: List<UploadFileRequest>): List<UploadFileResponse>
}