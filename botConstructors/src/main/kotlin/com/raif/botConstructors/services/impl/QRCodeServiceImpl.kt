package com.raif.botConstructors.services.impl

import com.raif.botConstructors.models.Order
import com.raif.botConstructors.services.QRCodeService
import com.raif.botConstructors.models.QR
import com.raif.botConstructors.models.RefundStatus
import com.raif.botConstructors.models.dto.RefundDto
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.util.*

@Service
class QRCodeServiceImpl(private val restTemplate: RestTemplate) : QRCodeService {

    private val logger = LoggerFactory.getLogger(javaClass)

    val qrMap: MutableMap<String, QR> = mutableMapOf()
    override fun createQR(amount: Double, orderId: String): QR {
        val url = "http://147.78.66.234:8081/payment-api/v1/qrs/dynamic"
        val request = mapOf("amount" to amount, "order" to orderId)
        val response: ResponseEntity<QR> = restTemplate.postForEntity(url, request, QR::class.java)
        val qr: QR = response.body ?: throw Exception("Failed to create QR")
        qrMap[qr.qrId] = qr
        return qr
    }

    override fun getQR(qrId: String): QR {
        val qr: QR = qrMap.getOrElse(qrId) {
            throw Exception("not valid qrId")
        }
        return qr
    }

    override fun updateAll() {
        logger.info("Updating all QRs")
        val url = "http://147.78.66.234:9091/database-api/v1/qrs"
        val qrs: Array<QR> = restTemplate.getForObject(url)
        for (qr: QR in qrs) {
            if (qrMap.contains(qr.qrId)) {
                qrMap[qr.qrId] = qr
            }
        }
        logger.info("All ${qrMap.size} QRs updated")
    }
    override fun refund(refundDto: RefundDto): RefundStatus {
        val url = "http://147.78.66.234:8081/payment-api/v1/qrs/refund"
        val refundStatus: RefundStatus? = restTemplate.postForObject(url, refundDto, RefundStatus::class.java)
        return refundStatus ?: throw Exception("Failed to get refund")
    }
}