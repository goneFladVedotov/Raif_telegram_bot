package com.raif.paymentapi.domain.dto

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated
import java.math.BigDecimal

@Validated
data class QrDynamicDto(
    @field:NotNull(message = "amount must be not null")
    val amount: BigDecimal,
    @field:NotNull(message = "order is required parameter")
    @field:Length(min = 1, max = 40, message = "order length must be 1..40")
    val order: String,
    @field:JsonFormat(pattern = "YYYY-MM-DD ТHH24:MM:SS±HH:MM / +nM / +nm")
    val qrExpirationDate: String?
)
