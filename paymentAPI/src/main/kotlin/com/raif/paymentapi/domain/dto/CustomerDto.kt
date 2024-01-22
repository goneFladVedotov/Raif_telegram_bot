package com.raif.paymentapi.domain.dto

import jakarta.validation.constraints.NotNull
import org.hibernate.sql.Alias
import org.springframework.validation.annotation.Validated

@Validated
data class CustomerDto(
    @NotNull(message = "bank alias must be not null")
    val bankAlias: String,
    @NotNull(message = "phone must be not null")
    val phone: String
)
