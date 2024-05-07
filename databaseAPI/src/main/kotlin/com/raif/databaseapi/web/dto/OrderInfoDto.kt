package com.raif.databaseapi.web.dto

import java.math.BigDecimal

data class OrderInfoDto(
    val orderId: String,
    val amount: BigDecimal,
    val comment: String?,
    val status: String,
    val createDate: String?,
    val expirationDate: String?,
    val qrId: String
)
