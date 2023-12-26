package com.raif.paymentapi.web.controller.impl

import com.raif.paymentapi.web.controller.QrController
import com.raif.paymentapi.domain.dto.QrDynamicDto
import com.raif.paymentapi.service.QrService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import raiffeisen.sbp.sdk.model.`in`.RefundStatus
import raiffeisen.sbp.sdk.model.out.QR
import raiffeisen.sbp.sdk.model.out.RefundInfo

@RestController
@RequestMapping("api/v1/qrs")
@Validated
class QrControllerImpl(private val qrService: QrService): QrController {
    @PostMapping("/dynamic")
    override fun registerQr(@Validated @RequestBody qrDynamicDto: QrDynamicDto): ResponseEntity<*> {
        return ResponseEntity.ok(qrService.registerDynamicQr(qrDynamicDto))
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