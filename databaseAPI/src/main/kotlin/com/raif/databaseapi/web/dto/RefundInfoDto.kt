package com.raif.databaseapi.web.dto

import jakarta.persistence.Column
import java.math.BigDecimal

data class RefundInfoDto(
    var amount: BigDecimal,
    var order: String,
    var refundId: String,
    var status: String,
    var paymentDetails: String,
    var transactionId: Long
)
