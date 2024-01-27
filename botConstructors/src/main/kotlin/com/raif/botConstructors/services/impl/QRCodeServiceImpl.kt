package com.raif.botConstructors.services.impl

import com.raif.botConstructors.services.QRCodeService
import com.raif.botConstructors.models.QR
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*

@Service
class QRCodeServiceImpl(private val restTemplate: RestTemplate) : QRCodeService {
    override fun getQR(amount: Double): QR {
        val url = "http://147.78.66.234:8081/payment-api/v1/qrs/dynamic"
//        val url = "http://host.docker.internal:8081/payment-api/v1/qrs/dynamic"
        val order = UUID.randomUUID().toString();
        val request = mapOf("amount" to amount, "order" to order)
        val response: ResponseEntity<QR> = restTemplate.postForEntity(url, request, QR::class.java)

        val qrId: String = response.body?.qrId ?: ""
        val qrStatus: String = response.body?.qrStatus ?: ""
        val payload: String = response.body?.payload ?: ""
        val qrUrl: String = response.body?.qrUrl ?: ""

        return QR(qrId, qrStatus, payload, qrUrl)
    }

    override fun updateQR(qr: QR): QR {
        val url = "http://147.78.66.234:9091/database-api/v1/qrs/"
//        val url = "http://host.docker.internal:9091/database-api/v1/qrs/"
        val response = restTemplate.getForObject(url + qr.qrId, QR::class.java)
        return response ?: throw Exception("Failed to get QR")

    }
}