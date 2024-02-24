package com.raif.paymentapi.domain.dto

import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated
import raiffeisen.sbp.sdk.model.`in`.fiscal.ReceiptType
import java.math.BigDecimal

@Validated
data class ReceiptDto(
    @field:NotNull(message = "receiptType must be not null")
    val receiptType: ReceiptType,
    @field:NotNull(message = "receiptNumber must be not null")
    val receiptNumber: String,
    @field:NotNull(message = "receiptClient must be not null")
    val receiptClientDto: ReceiptClientDto,
    @field:NotNull(message = "receiptItemDtos must be not null")
    val receiptItemDtos: Array<ReceiptItemDto>,
    @field:NotNull(message = "total must be not null")
    val total: BigDecimal
)
