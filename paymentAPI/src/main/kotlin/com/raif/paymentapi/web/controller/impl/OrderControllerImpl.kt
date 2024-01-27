package com.raif.paymentapi.web.controller.impl

import com.raif.paymentapi.domain.dto.OrderDto
import com.raif.paymentapi.domain.dto.RefundDto
import com.raif.paymentapi.service.OrderService
import com.raif.paymentapi.web.controller.OrderController
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping("payment-api/v1/orders")
class OrderControllerImpl(
    private val orderService: OrderService
) : OrderController {
    @PostMapping
    override fun makeOrder(@Validated @RequestBody orderDto: OrderDto): ResponseEntity<*> {
        orderService.makeOrder(orderDto)
        return ResponseEntity.ok("OK")
    }

    @PostMapping("/cancel")
    override fun cancelOrder(@RequestParam orderId: String): ResponseEntity<*> {
        orderService.cancelOrder(orderId)
        return ResponseEntity.ok("OK")
    }

    @PostMapping("/refund")
    override fun refundOrder(@Validated @RequestBody refundDto: RefundDto): ResponseEntity<*> {
        orderService.refundOrder(refundDto)
        return ResponseEntity.ok("OK")
    }
}