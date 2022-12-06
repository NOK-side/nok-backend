package com.example.nokbackend.application

import com.example.nokbackend.util.toJsonString
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream


@Component
class QRCodeService {
    fun createQRCode(data: Any): ByteArray {
        val matrix: BitMatrix = MultiFormatWriter().encode(data.toJsonString(), BarcodeFormat.QR_CODE, width, height)

        return ByteArrayOutputStream().use { out ->
            MatrixToImageWriter.writeToStream(matrix, "PNG", out)
            out.toByteArray()
        }
    }

    companion object {
        private const val width = 200
        private const val height = 200
    }
}
