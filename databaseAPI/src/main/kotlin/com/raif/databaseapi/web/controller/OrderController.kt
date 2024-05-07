package com.raif.databaseapi.web.controller

import com.raif.databaseapi.domain.OrderInfo
import com.raif.databaseapi.web.dto.OrderInfoDto
import org.springframework.http.ResponseEntity

interface OrderController {
    fun create(orderInfoDto: OrderInfoDto) : ResponseEntity<*>
    fun update(orderId:String, status:String) : ResponseEntity<*>
    fun get(orderId: String): ResponseEntity<OrderInfo>
    fun getAll(): ResponseEntity<List<OrderInfo>>
}