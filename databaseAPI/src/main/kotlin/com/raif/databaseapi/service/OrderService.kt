package com.raif.databaseapi.service

import com.raif.databaseapi.domain.OrderInfo

interface OrderService {
    fun create(orderInfo: OrderInfo)
    fun update(orderId: String, status: String)
    fun get(orderId: String) : OrderInfo
    fun getAll() : List<OrderInfo>
}