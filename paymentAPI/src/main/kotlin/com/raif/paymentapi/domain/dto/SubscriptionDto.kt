package com.raif.paymentapi.domain.dto

import org.springframework.validation.annotation.Validated

@Validated
data class SubscriptionDto(
    val id: String,
    val subscriptionPurpose: String,
    val redirectUrl: String?
)
