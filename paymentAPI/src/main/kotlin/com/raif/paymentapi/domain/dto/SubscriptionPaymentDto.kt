package com.raif.paymentapi.domain.dto

import java.math.BigDecimal

data class SubscriptionPaymentDto(
    val additionalInfo: String?,
    val amount: BigDecimal,
    val currency: String?,
    val order: String
)
