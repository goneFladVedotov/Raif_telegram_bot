package com.raif.paymentapi.domain.dto

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.URL
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import java.math.BigDecimal

@Validated
data class QrDynamicDto(
/*    @field:NotNull(message = "sbpMerchantId is required parameter")
    val sbpMerchantId: String,
    @field:NotNull(message = "secretKey is required parameter")
    val secretKey: String,*/
    @field:Length(max = 20, message = "account length must be less or equal 20")
    val account: String?,
    @field:Length(max = 140, message = "additionalInfo length must be less or equal 140")
    val additionalInfo: String?,
    @field:NotNull(message = "amount must be not null")
    val amount: BigDecimal,
    @field:Length(max = 3, min = 3, message = "currency must be equal 3")
    val currency: String?,
    @field:NotNull(message = "order is required parameter")
    @field:Length(min = 1, max = 40, message = "order length must be 1..40")
    val order: String,
    @field:Length(max = 185, message = "paymentDetails length must be less or equal 185")
    val paymentDetails: String?,
    @field:JsonFormat(pattern = "YYYY-MM-DD ТHH24:MM:SS±HH:MM / +nM / +nm")
    val qrExpirationDate: String?,
    val subscription: SubscriptionDto?,
    @field:URL(protocol = "https", message = "Invalid URL")
    val redirectUrl: String?,
    @field:Length(max = 32, message = "qrDescription length must be less or equal 32")
    val qrDescription: String?,
    val extra: Map<String, String>?
)
