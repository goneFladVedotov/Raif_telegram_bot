package com.raif.botConstructors.models

import java.math.BigDecimal

data class RefundStatus(
    val amount: BigDecimal,
    val refundStatus: String
)