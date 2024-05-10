package com.raif.paymentapi.web.controller

import com.raif.paymentapi.domain.dto.ReceiptDto
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import raiffeisen.sbp.sdk.model.fiscal.ReceiptInfo

interface ReceiptController {
    fun saveSellReceipt(@Valid @RequestBody receiptDto: ReceiptDto): ResponseEntity<ReceiptInfo>
    fun registerSellReceipt(receiptNumber: String): ResponseEntity<ReceiptInfo>
    fun getSellReceipt(receiptNumber: String): ResponseEntity<ReceiptInfo>
    fun saveRefundReceipt(@Valid @RequestBody receiptDto: ReceiptDto): ResponseEntity<ReceiptInfo>
    fun registerRefundReceipt(receiptNumber: String): ResponseEntity<ReceiptInfo>
    fun getRefundReceipt(receiptNumber: String): ResponseEntity<ReceiptInfo>
}