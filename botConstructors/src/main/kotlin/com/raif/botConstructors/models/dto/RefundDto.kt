package com.raif.botConstructors.models.dto

import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated
import java.math.BigDecimal

@Validated
data class RefundDto(
    @field:NotNull(message = "orderId must be not null")
    val orderId: String,
    @field:NotNull(message = "refundId must be not null")
    val refundId: String,
    @field:NotNull(message = "amount must be not null")
    val amount: BigDecimal,
    @field:NotNull(message = "paymentDetails must be not null")
    @field:Length(max = 140, message = "payment details length must be less or equal than 140")
    val paymentDetails: String,
    val customer: CustomerDto?
)