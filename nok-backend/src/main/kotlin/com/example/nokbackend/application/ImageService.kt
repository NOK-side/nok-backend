package com.example.nokbackend.application

import com.google.firebase.cloud.StorageClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

@Component
class ImageService {

    @Value("\${app.firebase-bucket}")
    private val firebaseBucket: String = ""

    fun uploadFile(multipartFile: MultipartFile, fileName: String): String {
        val bucket = StorageClient.getInstance().bucket(firebaseBucket)
        val inputStream = ByteArrayInputStream(multipartFile.bytes)
        bucket.create(fileName, inputStream, multipartFile.contentType)

        return PREFIX + fileName + SUFFIX
    }

    companion object {
        private const val PREFIX = "https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/"
        private const val SUFFIX = "?alt=media"
    }
}