package com.raif.paymentapi.domain.dto

import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated

@Validated
data class SbpClientDto(
    @NotNull(message = "merchantId must be not null")
    val merchantId: String,
    @NotNull(message = "secretKey must be not null")
    val secretKey: String
) {
}
