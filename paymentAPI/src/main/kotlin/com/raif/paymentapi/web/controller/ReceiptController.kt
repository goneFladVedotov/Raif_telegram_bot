package com.raif.paymentapi.web.controller

import com.raif.paymentapi.domain.dto.ReceiptDto
import org.springframework.http.ResponseEntity
import raiffeisen.sbp.sdk.model.`in`.fiscal.ReceiptInfo

interface ReceiptController {
    fun saveSellReceipt(receiptDto: ReceiptDto): ResponseEntity<ReceiptInfo>
    fun registerSellReceipt(receiptNumber: String): ResponseEntity<ReceiptInfo>
    fun saveRefundReceipt(receiptDto: ReceiptDto): ResponseEntity<ReceiptInfo>
    fun registerRefundReceipt(receiptNumber: String): ResponseEntity<ReceiptInfo>
}