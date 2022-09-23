package com.example.nokbackend.application

import org.springframework.web.multipart.MultipartFile

data class UploadFileRequest(
    val name: String,
    val file: MultipartFile
)

data class UploadFileResponse(
    val index: Int,
    val url: String
)

data class DeleteFileRequest(
    val url: String
)