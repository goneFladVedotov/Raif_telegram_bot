package com.raif.paymentapi.domain.model

import java.time.LocalDateTime

data class ResponseBody(
    val qrId: String,
    val qrStatus: String,
    val qrExpirationDate: LocalDateTime,
    val payload: String,
    val qrUrl: String,
    val subscriptionId: String
)
