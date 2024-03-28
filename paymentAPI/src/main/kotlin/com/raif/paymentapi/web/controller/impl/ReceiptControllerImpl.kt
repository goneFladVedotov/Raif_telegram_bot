package com.raif.paymentapi.web.controller.impl

import com.raif.paymentapi.domain.dto.ReceiptDto
import com.raif.paymentapi.service.ReceiptService
import com.raif.paymentapi.web.controller.ReceiptController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import raiffeisen.sbp.sdk.model.`in`.fiscal.ReceiptInfo

@RestController
@RequestMapping("/payment-api/v1/receipt")
@Validated
@Tag(name = "Receipt Controller",
    description = "Управляет чеками")
class ReceiptControllerImpl(
    private val receiptService: ReceiptService
) : ReceiptController {

    @PostMapping("/sell")
    @Operation(summary = "Создание чека на оплату")
    override fun saveSellReceipt(@Valid @RequestBody receiptDto: ReceiptDto): ResponseEntity<ReceiptInfo> {
        return ResponseEntity.ok(receiptService.saveSellReceipt(receiptDto))
    }

    @PutMapping("/sell/{receiptNumber}")
    @Operation(summary = "Регистрация чека на оплату")
    override fun registerSellReceipt(@PathVariable receiptNumber: String): ResponseEntity<ReceiptInfo> {
        return ResponseEntity.ok(receiptService.registerSellReceipt(receiptNumber))
    }

    @GetMapping("/sell/{receiptNumber}")
    @Operation(summary = "Получение чека на оплату")
    override fun getSellReceipt(@PathVariable receiptNumber: String): ResponseEntity<ReceiptInfo> {
        return ResponseEntity.ok(receiptService.getSellReceipt(receiptNumber))
    }

    @PostMapping("/refund")
    @Operation(summary = "Создание чека на возврат")
    override fun saveRefundReceipt(@Valid @RequestBody receiptDto: ReceiptDto): ResponseEntity<ReceiptInfo> {
        return ResponseEntity.ok(receiptService.saveRefundReceipt(receiptDto))
    }

    @PutMapping("/refund/{receiptNumber}")
    @Operation(summary = "Регистрация чека на возврат")
    override fun registerRefundReceipt(@PathVariable receiptNumber: String): ResponseEntity<ReceiptInfo> {
        return ResponseEntity.ok(receiptService.registerRefundReceipt(receiptNumber))
    }

    @GetMapping("/refund/{receiptNumber}")
    @Operation(summary = "Получение чека на возврат")
    override fun getRefundReceipt(@PathVariable receiptNumber: String): ResponseEntity<ReceiptInfo> {
        return ResponseEntity.ok(receiptService.getRefundReceipt(receiptNumber))
    }
}