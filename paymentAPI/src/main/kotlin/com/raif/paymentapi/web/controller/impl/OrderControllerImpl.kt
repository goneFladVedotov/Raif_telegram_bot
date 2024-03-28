package com.raif.paymentapi.web.controller.impl

import com.raif.paymentapi.domain.dto.OrderDto
import com.raif.paymentapi.domain.dto.RefundDto
import com.raif.paymentapi.service.OrderService
import com.raif.paymentapi.web.controller.OrderController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping("/payment-api/v1/orders")
@Tag(name = "Order Controller",
    description = "Управляет заказами")
class OrderControllerImpl(
    private val orderService: OrderService
) : OrderController {
    @PostMapping
    @Operation(summary = "Создание заказа")
    override fun makeOrder(@Validated @RequestBody orderDto: OrderDto): ResponseEntity<*> {
        orderService.makeOrder(orderDto)
        return ResponseEntity.ok("OK")
    }

    @PostMapping("/cancel")
    @Operation(summary = "Отмена заказа")
    override fun cancelOrder(@RequestParam orderId: String): ResponseEntity<*> {
        orderService.cancelOrder(orderId)
        return ResponseEntity.ok("OK")
    }

    @PostMapping("/refund")
    @Operation(summary = "Возврат заказа")
    override fun refundOrder(@Validated @RequestBody refundDto: RefundDto): ResponseEntity<*> {
        orderService.refundOrder(refundDto)
        return ResponseEntity.ok("OK")
    }
}