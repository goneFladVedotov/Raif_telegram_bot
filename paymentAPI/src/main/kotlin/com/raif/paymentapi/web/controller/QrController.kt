package com.raif.paymentapi.web.controller

import com.raif.paymentapi.domain.dto.*
import org.springframework.http.ResponseEntity
import raiffeisen.sbp.sdk.model.`in`.QRUrl
import raiffeisen.sbp.sdk.model.`in`.RefundStatus

interface QrController {
    fun registerDynamicQr(qrDynamicDto: QrDynamicDto): ResponseEntity<QRUrl>
    fun registerStaticQr(qrStaticDto: QrStaticDto): ResponseEntity<QRUrl>
    fun registerVariableQr(qrVariableDto: QrVariableDto): ResponseEntity<QRUrl>
    fun getQr(qrId: String): ResponseEntity<QRUrl>
    fun refundPayment(refundDto: RefundDto): ResponseEntity<RefundStatus>
}