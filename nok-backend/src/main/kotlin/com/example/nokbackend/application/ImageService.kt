package com.example.nokbackend.application

import com.google.cloud.storage.Bucket
import com.google.firebase.cloud.StorageClient
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

@Component
class ImageService(
    private val uuidGenerator: UUIDGenerator
) {

    @Value("\${app.firebase-bucket}")
    private val firebaseBucket: String = ""

    fun uploadFiles(multipartFiles: List<MultipartFile>): MutableList<ImageUploadResponse> {
        val bucket = StorageClient.getInstance().bucket(firebaseBucket)

        return runBlocking {
            val imageUploadResponses: MutableList<ImageUploadResponse> = mutableListOf()

            multipartFiles.forEachIndexed { index, multipartFile ->
                async {
                    val fileUrl = uploadFile(multipartFile, bucket)
                    imageUploadResponses.add(ImageUploadResponse(index, fileUrl))
                }
            }

            imageUploadResponses
        }
    }

    private suspend fun uploadFile(multipartFile: MultipartFile, bucket: Bucket): String {
        val fileName = uuidGenerator.generate(20)
        val inputStream = ByteArrayInputStream(multipartFile.bytes)
        bucket.create(fileName, inputStream, multipartFile.contentType)
        return PREFIX + fileName + SUFFIX
    }

    companion object {
        private const val PREFIX = "https://firebasestorage.googleapis.com/v0/b/nok-storage.appspot.com/o/"
        private const val SUFFIX = "?alt=media"
    }
}

data class ImageUploadResponse(
    val index: Int,
    val url: String
)