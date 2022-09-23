package com.example.nokbackend.domain.firebase

import com.example.nokbackend.application.DeleteFileRequest
import com.example.nokbackend.application.UploadFileRequest
import com.example.nokbackend.application.UploadFileResponse

interface Firebase {

    fun uploadFile(uploadFileRequests: List<UploadFileRequest>): List<UploadFileResponse>

    fun deleteFile(deleteFileRequests: List<DeleteFileRequest>)
}