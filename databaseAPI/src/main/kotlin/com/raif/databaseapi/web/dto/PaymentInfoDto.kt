package com.raif.databaseapi.web.dto

import jakarta.persistence.Column
import java.math.BigDecimal
import java.time.ZonedDateTime

data class PaymentInfoDto(
    var additionalInfo: String,
    var amount: BigDecimal,
    var createDate: ZonedDateTime,
    var currency: String,
    var order: String,
    var paymentStatus: String,
    var qrId: String,
    var sbpMerchantId: String,
    var transactionDate: ZonedDateTime,
    var transactionId: Long
)
