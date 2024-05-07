package com.raif.paymentapi.web.controller.impl

import com.raif.paymentapi.domain.dto.OrderDto
import com.raif.paymentapi.domain.dto.RefundDto
import com.raif.paymentapi.service.OrderService
import com.raif.paymentapi.service.RefundService
import com.raif.paymentapi.web.controller.OrderController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import raiffeisen.sbp.sdk.model.`in`.OrderInfo

@RestController
@Validated
@RequestMapping("/payment-api/v1/orders")
@Tag(
    name = "Order Controller",
    description = "Управляет заказами"
)
class OrderControllerImpl(
    private val orderService: OrderService,
    private val refundService: RefundService
) : OrderController {
    @PostMapping
    @Operation(summary = "Создание заказа")
    override fun makeOrder(@Validated @RequestBody orderDto: OrderDto): ResponseEntity<OrderInfo> {
        return ResponseEntity.ok(orderService.makeOrder(orderDto))
    }

    @PostMapping("/cancel/{orderId}")
    @Operation(summary = "Отмена заказа")
    override fun cancelOrder(@PathVariable orderId: String): ResponseEntity<*> {
        orderService.cancelOrder(orderId)
        return ResponseEntity.ok(null)
    }

    @PostMapping("/refund")
    @Operation(summary = "Возврат заказа")
    override fun refundOrder(@RequestBody refundDto: RefundDto): ResponseEntity<*> {
        return ResponseEntity.ok(refundService.makeOrderRefund(refundDto))
    }
}
