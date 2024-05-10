package com.raif.paymentapi.domain.dto

import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated
import java.math.BigDecimal

@Validated
data class ReceiptDto(
    @field:NotNull(message = "receiptNumber must be not null")
    val receiptNumber: String,
    @field:NotNull(message = "email must be not null")
    val email: String,
    @field:NotNull(message = "receiptItemDtos must be not null")
    val receiptItemDtos: Array<ReceiptItemDto>,
    @field:NotNull(message = "total must be not null")
    val total: BigDecimal
)
