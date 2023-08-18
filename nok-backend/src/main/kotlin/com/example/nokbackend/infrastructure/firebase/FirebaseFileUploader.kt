package com.example.nokbackend.infrastructure.firebase

import com.example.nokbackend.application.image.DeleteFileRequest
import com.example.nokbackend.application.image.UploadFileRequest
import com.example.nokbackend.application.image.UploadFileResponse
import com.google.cloud.storage.Bucket
import com.google.firebase.FirebaseApp
import com.google.firebase.cloud.StorageClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream

@Component
class FirebaseFileUploader {

    @Value("\${app.firebase-bucket}")
    private val firebaseBucket: String = ""

    fun uploadFiles(uploadFileRequests: List<UploadFileRequest>): List<UploadFileResponse> {
        val bucket = FirebaseApp.getApps()
            .findLast { it.name.contains("storage") }
            .let {
                StorageClient.getInstance(it).bucket(firebaseBucket)
            }

        return uploadFileRequests.mapIndexed { index, uploadFileRequest ->
            val fileUrl = uploadFile(uploadFileRequest, bucket)
            UploadFileResponse(index, fileUrl)
        }
    }

    fun deleteFiles(deleteFileRequests: List<DeleteFileRequest>) {
        val bucket = StorageClient.getInstance().bucket(firebaseBucket)

        deleteFileRequests.forEach {
            deleteFile(getFilenameFromUrl(it.url), bucket)
        }
    }

    private fun deleteFile(url: String, bucket: Bucket) {
        val filenameFromUrl = getFilenameFromUrl(url)
        bucket.get(filenameFromUrl).delete()
    }

    private fun uploadFile(uploadFileRequest: UploadFileRequest, bucket: Bucket): String {
        val inputStream = ByteArrayInputStream(uploadFileRequest.file.bytes)
        bucket.create(uploadFileRequest.name, inputStream, uploadFileRequest.file.contentType)

        return PREFIX + uploadFileRequest.name + SUFFIX
    }

    private fun getFilenameFromUrl(url: String): String = url.removePrefix(PREFIX).removeSuffix(SUFFIX)


    companion object {
        private const val PREFIX = "https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/"
        private const val SUFFIX = "?alt=media"
    }
}