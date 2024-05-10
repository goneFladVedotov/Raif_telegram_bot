package com.raif.databaseapi.service.impl

import com.raif.databaseapi.data.OrderRepository
import com.raif.databaseapi.domain.OrderInfo
import com.raif.databaseapi.service.OrderService
import com.raif.databaseapi.service.QrService
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val qrService: QrService
) : OrderService{
    override fun create(orderInfo: OrderInfo) {
        if (orderRepository.findByOrderId(orderInfo.orderId) != null) {
            throw IllegalStateException("order already exists")
        }
        qrService.getQrInfo(orderInfo.qrId)
        orderRepository.save(orderInfo)
    }

    override fun update(orderId: String, status: String) {
        val orderToUpdate = orderRepository.findByOrderId(orderId)?:
        throw IllegalArgumentException("order not found")
        orderToUpdate.status = status
        orderRepository.saveAndFlush(orderToUpdate)
    }

    override fun get(orderId: String): OrderInfo {
        return orderRepository.findByOrderId(orderId)?: throw IllegalArgumentException("order not found")
    }

    override fun getAll(): List<OrderInfo> {
        return orderRepository.findAll()
    }
}