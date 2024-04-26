package com.raif.botConstructors.services

import com.raif.botConstructors.models.Order
import org.springframework.web.bind.annotation.RequestParam
import java.math.BigDecimal

interface RefundService {
    fun refund(order: Order, amount: BigDecimal, details: String)

}