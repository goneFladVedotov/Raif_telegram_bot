package com.raif.paymentapi.domain.dto

import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated
import raiffeisen.sbp.sdk.model.fiscal.VatType
import java.math.BigDecimal

@Validated
data class ReceiptItemDto(
    @field:NotNull(message = "name must be not null")
    @field:Length(max = 128, message = "name must be less than 128")
    val name: String,
    @field:NotNull(message = "price must be not null")
    val price: BigDecimal,
    @field:NotNull(message = "quantity must be not null")
    val quantity: BigDecimal,
    @field:NotNull(message = "amount must be not null")
    val amount: BigDecimal,
    @field:NotNull(message = "vatType must be not null")
    val vatType: VatType
)
