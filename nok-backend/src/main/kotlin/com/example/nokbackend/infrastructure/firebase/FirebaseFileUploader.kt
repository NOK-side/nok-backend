package com.example.nokbackend.infrastructure.firebase

import arrow.core.Either
import arrow.core.getOrElse
import com.example.nokbackend.application.DeleteFileRequest
import com.example.nokbackend.application.UploadFileRequest
import com.example.nokbackend.application.UploadFileResponse
import com.google.cloud.storage.Bucket
import com.google.firebase.cloud.StorageClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream

@Component
class FirebaseFileUploader {

    @Value("\${app.firebase-bucket}")
    private val firebaseBucket: String = ""

    fun uploadFiles(uploadFileRequests: List<UploadFileRequest>): List<UploadFileResponse> {
        val bucket = StorageClient.getInstance().bucket(firebaseBucket)
        val uploadFileResponse: MutableList<UploadFileResponse> = mutableListOf()

        Either.catch {
            runBlocking {
                uploadFileRequests.forEachIndexed { index, uploadFileRequest ->
                    launch {
                        val fileUrl = uploadFile(uploadFileRequest, bucket)
                        uploadFileResponse.add(UploadFileResponse(index, fileUrl))
                    }
                }
            }
        }.getOrElse {
            uploadFileResponse.forEach {
                deleteFile(it.url, bucket)
            }

            throw RuntimeException("파일 업로드에 실패하였습니다")
        }

        return uploadFileResponse
    }

    fun deleteFiles(deleteFileRequests: List<DeleteFileRequest>) {
        val bucket = StorageClient.getInstance().bucket(firebaseBucket)

        Either.catch {
            runBlocking {
                deleteFileRequests.forEach {
                    launch {
                        deleteFile(getFilenameFromUrl(it.url), bucket)
                    }
                }
            }
        }.getOrElse {
            throw RuntimeException("파일 삭제에 실패하였습니다")
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