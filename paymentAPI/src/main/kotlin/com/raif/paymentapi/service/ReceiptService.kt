package com.raif.paymentapi.service

import com.raif.paymentapi.domain.dto.ReceiptDto
import raiffeisen.sbp.sdk.model.`in`.fiscal.ReceiptInfo

interface ReceiptService {
    fun saveSellReceipt(receiptDto: ReceiptDto): ReceiptInfo
    fun registerSellReceipt(receiptNumber: String): ReceiptInfo
    fun saveRefundReceipt(receiptDto: ReceiptDto): ReceiptInfo
    fun registerRefundReceipt(receiptNumber: String): ReceiptInfo
}