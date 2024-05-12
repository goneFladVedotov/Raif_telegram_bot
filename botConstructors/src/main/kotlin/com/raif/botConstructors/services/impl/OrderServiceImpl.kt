package com.raif.botConstructors.services.impl

import com.raif.botConstructors.models.Order
import com.raif.botConstructors.services.OrderService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl : OrderService {
    private val logger = LoggerFactory.getLogger(javaClass)

    val orderMap: MutableMap<String, Order> = mutableMapOf()
    override fun createOrder(order: Order) {
        val orderId = order.type + order.id
        orderMap[orderId] = order
    }

    override fun getOrder(orderId: String): Order {
        val order: Order = orderMap.getOrElse(orderId) {
            logger.error("Tried to get order $orderId but we only have ${orderMap.values.toList()}")
            throw Exception("not valid orderId")
        }
        return order
    }

    override fun getAll(): List<Order> {
        return orderMap.values.toList()
    }
}