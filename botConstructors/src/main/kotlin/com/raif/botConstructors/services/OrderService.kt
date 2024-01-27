package com.raif.botConstructors.services

import com.raif.botConstructors.models.Order
import com.raif.botConstructors.models.QR

interface OrderService {
    fun createOrder(id: String, type: String, amount: Double): Order

    fun getStatus(orderId: String): String //orderId = type + id

}