package com.raif.paymentapi.domain.model

import java.math.BigDecimal

data class OrderInformation(
    val orderId: String,
    val amount: BigDecimal,
    val comment: String,
    val status: String,
    val createDate: String,
    val expirationDate: String,
    val qrId: String
) {

}
