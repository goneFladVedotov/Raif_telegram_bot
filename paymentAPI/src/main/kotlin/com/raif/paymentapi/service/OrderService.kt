package com.raif.paymentapi.service

import com.raif.paymentapi.domain.dto.OrderDto
import raiffeisen.sbp.sdk.model.`in`.OrderInfo

interface OrderService {
    fun makeOrder(orderDto: OrderDto): OrderInfo
    fun cancelOrder(orderId: String)
}