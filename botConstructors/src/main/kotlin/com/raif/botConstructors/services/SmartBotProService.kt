package com.raif.botConstructors.services

import com.raif.botConstructors.models.Order
import com.raif.botConstructors.models.dto.RefundDto

interface SmartBotProService {
    fun orderPaid(order: Order)
    fun orderRefund(order: Order, refundDto: RefundDto)
}