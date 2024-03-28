package com.raif.paymentapi.web.controller.impl

import com.raif.paymentapi.domain.dto.*
import com.raif.paymentapi.service.QrService
import com.raif.paymentapi.service.RefundService
import com.raif.paymentapi.web.controller.QrController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import raiffeisen.sbp.sdk.model.`in`.QRUrl
import raiffeisen.sbp.sdk.model.`in`.RefundStatus

@RestController
@RequestMapping("/payment-api/v1/qrs")
@Validated
@Tag(name = "QR Controller",
    description = "Создает 3 типа QR-кодов и делает рефанды")
class QrControllerImpl(
    private val qrService: QrService,
    private val refundService: RefundService
) : QrController {
    @PostMapping("/dynamic")
    @Operation(summary = "Создание динамического QR")
    override fun registerDynamicQr(@Validated @RequestBody qrDynamicDto: QrDynamicDto): ResponseEntity<QRUrl> {
        return ResponseEntity.ok(qrService.registerDynamicQr(qrDynamicDto))
    }

    @PostMapping("/static")
    @Operation(summary = "Создание статического QR")
    override fun registerStaticQr(@Validated @RequestBody qrStaticDto: QrStaticDto): ResponseEntity<QRUrl> {
        return ResponseEntity.ok(qrService.registerStaticQr(qrStaticDto))
    }

    @PostMapping("/variable")
    @Operation(summary = "Создание кассовой ссылки СБП")
    override fun registerVariableQr(@Validated @RequestBody qrVariableDto: QrVariableDto): ResponseEntity<QRUrl> {
        return ResponseEntity.ok(qrService.registerVariableQr(qrVariableDto))
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о QR")
    override fun getQr(@PathVariable("id") qrId: String): ResponseEntity<QRUrl> {
        return ResponseEntity.ok(qrService.getQrInfo(qrId))
    }

    @PostMapping("/refund")
    @Operation(summary = "Возврат платежа")
    override fun refundPayment(@RequestBody refundDto: RefundDto): ResponseEntity<RefundStatus> {
        return ResponseEntity.ok(refundService.makeRefund(refundDto))
    }
}