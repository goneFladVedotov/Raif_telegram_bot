package com.raif.paymentapi.domain.dto

import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated

@Validated
data class SbpClientDto(
    @field:NotNull(message = "merchantId must be not null")
    val merchantId: String,
    @field:NotNull(message = "secretKey must be not null")
    val secretKey: String
) {
}
