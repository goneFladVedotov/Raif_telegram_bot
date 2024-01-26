package com.raif.paymentapi.web.controller.impl

import com.raif.paymentapi.domain.dto.OrderDto
import com.raif.paymentapi.domain.dto.RefundDto
import com.raif.paymentapi.service.OrderService
import com.raif.paymentapi.web.controller.OrderController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("payment-api/v1/orders")
class OrderControllerImpl(
    private val orderService: OrderService
) : OrderController {
    @PostMapping
    override fun makeOrder(@RequestBody orderDto: OrderDto): ResponseEntity<*> {
        orderService.makeOrder(orderDto)
        return ResponseEntity.ok("OK")
    }

    @PostMapping("/cancel")
    override fun cancelOrder(@RequestParam orderId: String): ResponseEntity<*> {
        orderService.cancelOrder(orderId)
        return ResponseEntity.ok("OK")
    }

    @PostMapping("/refund")
    override fun refundOrder(@RequestBody refundDto: RefundDto): ResponseEntity<*> {
        orderService.refundOrder(refundDto)
        return ResponseEntity.ok("OK")
    }
}