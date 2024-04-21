package com.raif.botConstructors.models.dto

import java.math.BigDecimal

data class ReceiptDto(
    val receiptNumber: String,
    val email: String,
    val receiptItemDtos: Array<ReceiptItemDto>,
    val total: BigDecimal
) {
    data class ReceiptItemDto(
        val name: String,
        val price: BigDecimal,
        val quantity: BigDecimal,
        val amount: BigDecimal,
        val vatType: String
    )
}