package com.example.nokbackend.application

interface Firebase {

    fun uploadFile(fileUploadFileRequests: List<UploadFileRequest>): List<UploadFileResponse>
}