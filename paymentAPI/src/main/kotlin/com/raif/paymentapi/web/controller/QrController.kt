package com.raif.paymentapi.web.controller

import com.raif.paymentapi.domain.dto.QrDynamicDto
import com.raif.paymentapi.domain.dto.QrStaticDto
import com.raif.paymentapi.domain.dto.QrVariableDto
import com.raif.paymentapi.domain.dto.SbpClientDto
import org.springframework.http.ResponseEntity
import raiffeisen.sbp.sdk.model.`in`.RefundStatus
import raiffeisen.sbp.sdk.model.out.RefundInfo

interface QrController {
    fun registerDynamicQr(qrDynamicDto: QrDynamicDto): ResponseEntity<*>
    fun registerStaticQr(qrStaticDto: QrStaticDto): ResponseEntity<*>
    fun registerVariableQr(qrVariableDto: QrVariableDto): ResponseEntity<*>
    fun getQr(qrId: String, sbpClientDto: SbpClientDto): ResponseEntity<*>
    fun getBanks(): List<String>
    fun refundPayment(orderId: String, refundId: String): RefundInfo
    fun getRefundStatus(): RefundStatus
}