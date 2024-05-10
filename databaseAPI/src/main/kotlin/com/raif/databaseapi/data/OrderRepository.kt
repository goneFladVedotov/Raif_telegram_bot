package com.raif.databaseapi.data

import com.raif.databaseapi.domain.OrderInfo
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<OrderInfo, Long> {
    override fun findAll() : List<OrderInfo>
    fun findByQrId(qrId: String) : List<OrderInfo>
    fun findByOrderId(orderId: String) : OrderInfo?
}