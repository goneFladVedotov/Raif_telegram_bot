package com.raif.paymentapi.controller.impl

import com.raif.paymentapi.controller.QrController
import com.raif.paymentapi.domain.dto.QrDto
import com.raif.paymentapi.service.QrService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import raiffeisen.sbp.sdk.model.`in`.QRUrl
import raiffeisen.sbp.sdk.model.`in`.RefundStatus
import raiffeisen.sbp.sdk.model.out.QR
import raiffeisen.sbp.sdk.model.out.RefundInfo

@RestController
@RequestMapping("api/v1/qrs")
@Validated
class QrControllerImpl(private val qrService: QrService): QrController {
    @PostMapping
    override fun registerQr(@Validated @RequestBody qrDto: QrDto): ResponseEntity<*> {
        return ResponseEntity.ok(qrService.registerQr(qrDto))
    }

    override fun cancelQr(qrId: Long) {
        TODO("Not yet implemented")
    }

    override fun getQr(qrId: Long): QR {
        TODO("Not yet implemented")
    }

    override fun getBanks(): List<String> {
        TODO("Not yet implemented")
    }

    override fun refundPayment(orderId: String, refundId: String): RefundInfo {
        TODO("Not yet implemented")
    }

    override fun getRefundStatus(): RefundStatus {
        TODO("Not yet implemented")
    }
}