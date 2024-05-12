package com.raif.botConstructors.services

import com.raif.botConstructors.models.Order

interface OrderService {
    fun createOrder(order: Order)
    fun getOrder(orderId: String): Order

    fun getAll(): List<Order>

}