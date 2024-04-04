package com.raif.paymentapi.domain.dto

data class SubscriptionPaymentDto(
    val additionalInfo: String?,
    val amount: Long,
    val currency: String?,
    val order: String
)
