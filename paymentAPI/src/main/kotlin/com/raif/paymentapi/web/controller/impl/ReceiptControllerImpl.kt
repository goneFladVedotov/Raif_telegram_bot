package com.raif.paymentapi.web.controller.impl

import com.raif.paymentapi.domain.dto.ReceiptDto
import com.raif.paymentapi.service.ReceiptService
import com.raif.paymentapi.web.controller.ReceiptController
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import raiffeisen.sbp.sdk.model.`in`.fiscal.ReceiptInfo

@RestController
@RequestMapping("/payment-api/v1/receipt")
@Validated
class ReceiptControllerImpl(
    private val receiptService: ReceiptService
) : ReceiptController {

    @PostMapping("/sell")
    override fun saveSellReceipt(@Valid @RequestBody receiptDto: ReceiptDto): ResponseEntity<ReceiptInfo> {
        return ResponseEntity.ok(receiptService.saveSellReceipt(receiptDto))
    }

    @PutMapping("/sell/{receiptNumber}")
    override fun registerSellReceipt(@PathVariable receiptNumber: String): ResponseEntity<ReceiptInfo> {
        return ResponseEntity.ok(receiptService.registerSellReceipt(receiptNumber))
    }

    @GetMapping("/sell/{receiptNumber}")
    override fun getSellReceipt(@PathVariable receiptNumber: String): ResponseEntity<ReceiptInfo> {
        return ResponseEntity.ok(receiptService.getSellReceipt(receiptNumber))
    }

    @PostMapping("/refund")
    override fun saveRefundReceipt(@Valid @RequestBody receiptDto: ReceiptDto): ResponseEntity<ReceiptInfo> {
        return ResponseEntity.ok(receiptService.saveRefundReceipt(receiptDto))
    }

    @PutMapping("/refund/{receiptNumber}")
    override fun registerRefundReceipt(@PathVariable receiptNumber: String): ResponseEntity<ReceiptInfo> {
        return ResponseEntity.ok(receiptService.registerRefundReceipt(receiptNumber))
    }

    @GetMapping("/refund/{receiptNumber}")
    override fun getRefundReceipt(@PathVariable receiptNumber: String): ResponseEntity<ReceiptInfo> {
        return ResponseEntity.ok(receiptService.getRefundReceipt(receiptNumber))
    }
}