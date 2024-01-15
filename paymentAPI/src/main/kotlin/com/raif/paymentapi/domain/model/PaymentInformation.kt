package com.raif.paymentapi.domain.model

import java.math.BigDecimal
import java.time.ZonedDateTime

data class PaymentInformation(
    var additionalInfo: String?,
    val amount: BigDecimal,
    val createDate: ZonedDateTime,
    val currency: String,
    val merchantId: Long,
    val order: String,
    val paymentStatus: String,
    val qrId: String,
    val sbpMerchantId: String,
    val transactionDate: ZonedDateTime,
    val transactionId: Long
) {
}
