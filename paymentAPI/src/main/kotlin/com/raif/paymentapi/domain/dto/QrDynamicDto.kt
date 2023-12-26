package com.raif.paymentapi.domain.dto

import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated
import java.math.BigDecimal
import java.time.LocalDateTime

@Validated
data class QrDynamicDto(
    val account: String?,
    val additionalInfo: String?,
    @NotNull(message = "amount must be not null")
    val amount: BigDecimal,
    val currency: String?,
    @NotNull(message = "order is required parameter")
    @Length(min = 1, max = 40, message = "order length must be 1..40")
    val order: String,
    val paymentDetails: String?,
    val qrExpirationDate: LocalDateTime?,
    //TODO subscription
    val redirectUrl: String?,
    val qrDescription: String?
    //TODO extra
    /*@NotNull(message = "sbpMerchantId is required parameter")
    val sbpMerchantId: String,*//*
    @NotNull(message = "secretKey is required parameter")
    val secretKey: String*/
)
