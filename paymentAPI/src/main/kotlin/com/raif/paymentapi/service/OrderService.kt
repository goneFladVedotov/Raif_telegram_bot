package com.raif.paymentapi.service

import com.raif.paymentapi.domain.dto.OrderDto
import com.raif.paymentapi.domain.dto.RefundDto

interface OrderService {
    fun makeOrder(orderDto: OrderDto)
    fun cancelOrder(orderId: String)
    fun refundOrder(refundDto: RefundDto)
}