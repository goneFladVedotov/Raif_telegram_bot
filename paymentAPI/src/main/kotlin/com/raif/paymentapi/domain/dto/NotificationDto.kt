package com.raif.paymentapi.domain.dto

import java.math.BigDecimal

data class NotificationDto(
    val transactionId: Long,

    val qrId: String,

    val sbpMerchantId: String,

    val merchantId: Long,

    val amount: BigDecimal,

    val currency: String,

    val transactionDate: String,

    val paymentStatus: String,

    val additionalInfo: String,

    val order: String,

    val createDate: String
)
