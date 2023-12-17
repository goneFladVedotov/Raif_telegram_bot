package com.raif.paymentapi.domain.dto

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import raiffeisen.sbp.sdk.model.QRType
import java.time.LocalDateTime

@Validated
data class QrDto(
    @NotNull(message = "qrType is required parameter")
    val qrType: QRType,
    @NotNull(message = "amount must be not null")
    val amount: Double,
    @NotNull(message = "order is required parameter")
    @Length(min = 1, max = 40)
    val order: String?,
    @NotNull(message = "sbpMerchantId is required parameter")
    val sbpMerchantId: String,
    @NotNull(message = "secretKey is required parameter")
    val secretKey: String
)
