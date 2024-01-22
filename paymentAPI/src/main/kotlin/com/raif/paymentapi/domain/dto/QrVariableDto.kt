package com.raif.paymentapi.domain.dto

import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.URL

data class QrVariableDto(
    /*@field:NotNull(message = "sbpMerchantId is required parameter")
    val sbpMerchantId: String,
    @field:NotNull(message = "secretKey is required parameter")
    val secretKey: String,*/
    @field:Length(max = 20, message = "account length must be less or equal 20")
    val account: String?,
    @field:URL(protocol = "https", message = "Invalid URL")
    val redirectUrl: String?,
    @field:Length(max = 32, message = "qrDescription length must be less or equal 32")
    val qrDescription: String?
)
