package com.raif.databaseapi.web.controller.impl

import com.raif.databaseapi.domain.OrderInfo
import com.raif.databaseapi.mapper.impl.OrderInfoMapper
import com.raif.databaseapi.service.OrderService
import com.raif.databaseapi.web.controller.OrderController
import com.raif.databaseapi.web.dto.OrderInfoDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("database-api/v1/orders")
@Tag(name = "Order Controller")
class OrderControllerImpl(
    private val orderInfoMapper: OrderInfoMapper,
    private val orderService: OrderService
) : OrderController {
    @PostMapping
    @Operation(summary = "Сохраняет заказ в БД")
    override fun create(@RequestBody orderInfoDto: OrderInfoDto): ResponseEntity<*> {
        orderService.create(orderInfoMapper.dtoToEntity(orderInfoDto))
        return ResponseEntity.ok(null)
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "Обновляет статус заказа в БД")
    override fun update(@PathVariable orderId: String, @RequestBody status: String): ResponseEntity<*> {
        orderService.update(orderId, status)
        return ResponseEntity.ok(null)
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Возвращает заказ по его ID")
    override fun get(@PathVariable orderId: String): ResponseEntity<OrderInfo> {
        return ResponseEntity.ok(orderService.get(orderId))
    }

    @GetMapping
    @Operation(summary = "Возвращает список всех заказов в БД")
    override fun getAll(): ResponseEntity<List<OrderInfo>> {
        return ResponseEntity.ok(orderService.getAll())
    }
}