package com.raif.paymentapi.domain.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated

@Validated
data class ReceiptClientDto(
    @field:NotNull(message = "email must be not null")
    @field:Length(max = 99, message = "email must be less than 99")
    @field:Email(message = "email must be correct")
    val email: String
)
