package com.raif.paymentapi.web.controller.impl

import com.raif.paymentapi.domain.dto.QrDynamicDto
import com.raif.paymentapi.domain.dto.QrStaticDto
import com.raif.paymentapi.domain.dto.QrVariableDto
import com.raif.paymentapi.domain.dto.SbpClientDto
import com.raif.paymentapi.service.QrService
import com.raif.paymentapi.web.controller.QrController
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import raiffeisen.sbp.sdk.model.`in`.RefundStatus
import raiffeisen.sbp.sdk.model.out.RefundInfo

@RestController
@RequestMapping("api/v1/qrs")
@Validated
class QrControllerImpl(private val qrService: QrService) : QrController {
    @PostMapping("/dynamic")
    override fun registerDynamicQr(@Validated @RequestBody qrDynamicDto: QrDynamicDto): ResponseEntity<*> {
        return ResponseEntity.ok(qrService.registerDynamicQr(qrDynamicDto))
    }

    @PostMapping("/static")
    override fun registerStaticQr(qrStaticDto: QrStaticDto): ResponseEntity<*> {
        return ResponseEntity.ok(qrService.registerStaticQr(qrStaticDto))
    }

    @PostMapping("/variable")
    override fun registerVariableQr(qrVariableDto: QrVariableDto): ResponseEntity<*> {
        return ResponseEntity.ok(qrService.registerVariableQr(qrVariableDto))
    }

    @GetMapping("/{id}")
    override fun getQr(@PathVariable("id") qrId: String, sbpClientDto: SbpClientDto): ResponseEntity<*> {
        return ResponseEntity.ok(qrService.getQrInfo(qrId, sbpClientDto))
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