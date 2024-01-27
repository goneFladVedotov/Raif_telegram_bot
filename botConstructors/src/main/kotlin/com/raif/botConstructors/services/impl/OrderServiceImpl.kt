package com.raif.botConstructors.services.impl

import com.raif.botConstructors.models.Order
import com.raif.botConstructors.services.OrderService
import com.raif.botConstructors.services.QRCodeService
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(private val qrCodeService: QRCodeService) : OrderService {
    val orderMap: MutableMap<String, Order> = mutableMapOf()
    override fun createOrder(id: String, type: String, amount: Double): Order {
        val orderId = type + id
        val order: Order = orderMap.getOrElse(orderId) {
            val qr = qrCodeService.getQR(amount)
            val newOrder = Order(id, type, amount, qr)
            orderMap[orderId] = newOrder
            return newOrder
        }
        return order
    }

    override fun getStatus(orderId: String): String {
        val order: Order = orderMap.getOrElse(orderId) {
            throw Exception("not valid orderId")
        }
        val newOrder = Order(order.id, order.type, order.amount, qrCodeService.updateQR(order.qr))
        orderMap[orderId] = newOrder
        return newOrder.qr.qrStatus
    }

}