package com.raif.paymentapi.domain.model

import java.math.BigDecimal

data class RefundInformation(
    val amount: BigDecimal,
    val order: String,
    val refundId: String,
    val paymentDetails: String?,
    val transactionId: Long
) {
}
