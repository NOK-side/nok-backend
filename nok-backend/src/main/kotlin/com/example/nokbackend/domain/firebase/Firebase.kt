package com.example.nokbackend.domain.firebase

import com.example.nokbackend.application.image.DeleteFileRequest
import com.example.nokbackend.application.image.UploadFileRequest
import com.example.nokbackend.application.image.UploadFileResponse

interface Firebase {

    fun uploadFile(uploadFileRequests: List<UploadFileRequest>): List<UploadFileResponse>

    fun deleteFile(deleteFileRequests: List<DeleteFileRequest>)
}