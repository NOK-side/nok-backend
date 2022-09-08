package com.example.nokbackend.infrastructure.firebase

import com.example.nokbackend.application.UploadFileResponse
import com.example.nokbackend.application.UploadFileRequest
import com.google.cloud.storage.Bucket
import com.google.firebase.cloud.StorageClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

@Component
class FirebaseFileUploader {

    @Value("\${app.firebase-bucket}")
    private val firebaseBucket: String = ""

    fun uploadFiles(uploadFileRequests: List<UploadFileRequest>): List<UploadFileResponse> {
        val bucket = StorageClient.getInstance().bucket(firebaseBucket)

        return runBlocking {
            val uploadFileResponse: MutableList<UploadFileResponse> = mutableListOf()

            uploadFileRequests.forEachIndexed { index, uploadFileRequest ->
                launch {
                    val fileUrl = uploadFile(
                        uploadFileRequest.name,
                        uploadFileRequest.file,
                        bucket
                    )
                    uploadFileResponse.add(UploadFileResponse(index, fileUrl))
                }
            }

            uploadFileResponse
        }
    }

    private suspend fun uploadFile(fileName: String, multipartFile: MultipartFile, bucket: Bucket): String {
        val inputStream = ByteArrayInputStream(multipartFile.bytes)
        bucket.create(fileName, inputStream, multipartFile.contentType)
        return PREFIX + fileName + SUFFIX
    }

    companion object {
        private const val PREFIX = "https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/"
        private const val SUFFIX = "?alt=media"
    }
}