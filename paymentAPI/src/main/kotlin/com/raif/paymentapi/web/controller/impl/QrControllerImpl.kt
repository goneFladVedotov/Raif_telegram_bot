package com.raif.paymentapi.web.controller.impl

import com.raif.paymentapi.domain.dto.*
import com.raif.paymentapi.service.QrService
import com.raif.paymentapi.service.RefundService
import com.raif.paymentapi.web.controller.QrController
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import raiffeisen.sbp.sdk.model.`in`.RefundStatus
import raiffeisen.sbp.sdk.model.out.RefundInfo

@RestController
@RequestMapping("payment-api/v1/qrs")
@Validated
class QrControllerImpl(
    private val qrService: QrService,
    private val refundService: RefundService
) : QrController {
    @PostMapping("/dynamic")
    override fun registerDynamicQr(@Validated @RequestBody qrDynamicDto: QrDynamicDto): ResponseEntity<*> {
        return ResponseEntity.ok(qrService.registerDynamicQr(qrDynamicDto))
    }

    @PostMapping("/static")
    override fun registerStaticQr(@Validated @RequestBody qrStaticDto: QrStaticDto): ResponseEntity<*> {
        return ResponseEntity.ok(qrService.registerStaticQr(qrStaticDto))
    }

    @PostMapping("/variable")
    override fun registerVariableQr(@Validated @RequestBody qrVariableDto: QrVariableDto): ResponseEntity<*> {
        return ResponseEntity.ok(qrService.registerVariableQr(qrVariableDto))
    }

    @GetMapping("/{id}")
    override fun getQr(@PathVariable("id") qrId: String, @Validated @RequestBody sbpClientDto: SbpClientDto): ResponseEntity<*> {
        return ResponseEntity.ok(qrService.getQrInfo(qrId))
    }

    @PostMapping("/refund")
    override fun refundPayment(@RequestBody refundDto: RefundDto): ResponseEntity<*> {
        return ResponseEntity.ok(refundService.makeRefund(refundDto))
    }
}