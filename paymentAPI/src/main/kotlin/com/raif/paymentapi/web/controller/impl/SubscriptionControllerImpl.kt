package com.raif.paymentapi.web.controller.impl

import com.raif.paymentapi.domain.dto.SubscriptionDto
import com.raif.paymentapi.domain.dto.SubscriptionPaymentDto
import com.raif.paymentapi.service.SubscriptionService
import com.raif.paymentapi.web.controller.SubscriptionController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import raiffeisen.sbp.sdk.model.`in`.SubscriptionInfo
import raiffeisen.sbp.sdk.model.`in`.SubscriptionPaymentResponse

@RestController
@RequestMapping("/payment-api/v1/subscription")
@Tag(name = "Subscription Controller",
    description = "Управляет подписками")
class SubscriptionControllerImpl(
    private val subscriptionService: SubscriptionService
) : SubscriptionController {
    @PostMapping
    @Operation(summary = "Создание QR для подписки")
    override fun createQr(@RequestBody subscriptionDto: SubscriptionDto): ResponseEntity<SubscriptionInfo> {
        return ResponseEntity.ok(subscriptionService.createQr(subscriptionDto))
    }

    @GetMapping("/{subscriptionId}")
    @Operation(summary = "Получение инфомарции о подписке")
    override fun getQrInfo(@PathVariable subscriptionId: String): ResponseEntity<SubscriptionInfo> {
        return ResponseEntity.ok(subscriptionService.getQrInfo(subscriptionId))
    }

    @GetMapping("/status/{subscriptionId}")
    @Operation(summary = "Получение статуса подписки")
    override fun getSubscriptionStatus(@PathVariable subscriptionId: String): ResponseEntity<String> {
        return ResponseEntity.ok(subscriptionService.getSubscriptionStatus(subscriptionId).toString())
    }

    @PostMapping("/pay/{subscriptionId}")
    @Operation(summary = "Оплата по подписке")
    override fun paySubscription(
        @PathVariable subscriptionId: String,
        @RequestBody dto: SubscriptionPaymentDto
    ): ResponseEntity<SubscriptionPaymentResponse> {
        return ResponseEntity.ok(subscriptionService.paySubscription(subscriptionId, dto))
    }

    @GetMapping("/pay/{subscriptionId}/orders/{order}")
    @Operation(summary = "Получение информации о платеже по подписке")
    override fun getSubscriptionPaymentInfo(
        @PathVariable("subscriptionId") subscriptionId: String,
        @PathVariable("order") order: String
    ): ResponseEntity<SubscriptionPaymentResponse> {
        return ResponseEntity.ok(subscriptionService.getSubscriptionPaymentInfo(subscriptionId, order))
    }
}
