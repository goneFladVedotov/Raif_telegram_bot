package com.raif.paymentapi.web.controller

import com.raif.paymentapi.domain.dto.QrDynamicDto
import org.springframework.http.ResponseEntity
import raiffeisen.sbp.sdk.model.`in`.RefundStatus
import raiffeisen.sbp.sdk.model.out.QR
import raiffeisen.sbp.sdk.model.out.RefundInfo

interface QrController {
    fun registerQr(qrDynamicDto: QrDynamicDto): ResponseEntity<*>
    fun cancelQr(qrId: Long)
    fun getQr(qrId: Long): QR
    fun getBanks(): List<String>
    fun refundPayment(orderId: String, refundId: String): RefundInfo
    fun getRefundStatus(): RefundStatus
}