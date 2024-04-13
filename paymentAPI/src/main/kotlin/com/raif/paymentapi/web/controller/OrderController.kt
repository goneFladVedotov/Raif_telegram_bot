package com.raif.paymentapi.web.controller

import com.raif.paymentapi.domain.dto.OrderDto
import org.springframework.http.ResponseEntity

interface OrderController {
    fun makeOrder(orderDto: OrderDto): ResponseEntity<*>
    fun cancelOrder(orderId: String): ResponseEntity<*>
}