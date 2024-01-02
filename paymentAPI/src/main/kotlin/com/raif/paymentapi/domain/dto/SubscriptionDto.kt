package com.raif.paymentapi.domain.dto

import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated

@Validated
data class SubscriptionDto(
    @field:Length(min = 1, max = 100, message = "id length must be less or equal 100 and more aor equal 1")
    val id: String,
    @field:NotNull(message = "subscriptionPurpose must be not null")
    val subscriptionPurpose: String
)
