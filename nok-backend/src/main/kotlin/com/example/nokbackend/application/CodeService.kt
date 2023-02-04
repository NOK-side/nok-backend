package com.example.nokbackend.application

import com.example.nokbackend.util.toJsonString
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream


@Component
class CodeService {
    fun createCodeImage(data: Any, codeType: CodeType): ByteArray {
        val matrix: BitMatrix = MultiFormatWriter().encode(data.toJsonString(), codeType.format, codeType.width, codeType.height)

        return ByteArrayOutputStream().use { out ->
            MatrixToImageWriter.writeToStream(matrix, "PNG", out)
            out.toByteArray()
        }
    }

    enum class CodeType(
        val width: Int,
        val height: Int,
        val format: BarcodeFormat
    ) {
        QRCODE(200, 200, BarcodeFormat.QR_CODE), BARCODE(200, 100, BarcodeFormat.CODE_128)
    }
}
