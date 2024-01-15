package com.raif.paymentapi.web.controller

import com.raif.paymentapi.domain.dto.*
import org.springframework.http.ResponseEntity
import raiffeisen.sbp.sdk.model.`in`.RefundStatus
import raiffeisen.sbp.sdk.model.out.RefundInfo

interface QrController {
    fun registerDynamicQr(qrDynamicDto: QrDynamicDto): ResponseEntity<*>
    fun registerStaticQr(qrStaticDto: QrStaticDto): ResponseEntity<*>
    fun registerVariableQr(qrVariableDto: QrVariableDto): ResponseEntity<*>
    fun getQr(qrId: String, sbpClientDto: SbpClientDto): ResponseEntity<*>
    fun refundPayment(refundDto: RefundDto): ResponseEntity<*>
}