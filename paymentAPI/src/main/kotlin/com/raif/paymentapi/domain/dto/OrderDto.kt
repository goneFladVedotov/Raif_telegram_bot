package com.raif.paymentapi.domain.dto

import org.springframework.validation.annotation.Validated
import java.math.BigDecimal

@Validated
data class OrderDto(
    val orderId: String,
    val amount: BigDecimal,
    val expirationDate: String?,
    val qrId: String
)
