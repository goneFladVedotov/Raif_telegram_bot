package com.raif.databaseapi.web.controller.impl

import com.raif.databaseapi.domain.SubscriptionInfo
import com.raif.databaseapi.mapper.Mapper
import com.raif.databaseapi.service.SubscriptionService
import com.raif.databaseapi.web.controller.SubscriptionController
import com.raif.databaseapi.web.dto.SubscriptionDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/payment-api/v1/subscription")
@Tag(name = "Subscription Controller")
class SubscriptionControllerImpl(
    private val subscriptionService: SubscriptionService,
    private val mapper: Mapper<SubscriptionInfo, SubscriptionDto>
) : SubscriptionController {
    @PostMapping
    @Operation(summary = "Сохранение информации о подписке")
    override fun save(@RequestBody subscriptionDto: SubscriptionDto): ResponseEntity<*> {
        subscriptionService.save(mapper.dtoToEntity(subscriptionDto))
        return ResponseEntity.ok(null)
    }

    @PatchMapping("/{subscriptionId}")
    @Operation(summary = "Обновление статуса подписки")
    override fun update(@PathVariable subscriptionId: String, @RequestBody status: String): ResponseEntity<*> {
        subscriptionService.update(subscriptionId, status)
        return ResponseEntity.ok(null)
    }

    @GetMapping("/{subscriptionId}")
    @Operation(summary = "Получение информации о подписке")
    override fun get(@PathVariable subscriptionId: String): ResponseEntity<SubscriptionDto> {
        return ResponseEntity.ok(mapper.entityToDto(subscriptionService.get(subscriptionId)))
    }

    @GetMapping
    @Operation(summary = "Получение всех подписок")
    override fun getAll(): ResponseEntity<List<SubscriptionDto>> {
        return ResponseEntity.ok(mapper.entityToDto(subscriptionService.getAll()))
    }
}