package com.raif.paymentapi.web.controller

import com.raif.paymentapi.domain.dto.SubscriptionDto
import com.raif.paymentapi.domain.dto.SubscriptionPaymentDto
import org.springframework.http.ResponseEntity
import raiffeisen.sbp.sdk.model.`in`.SubscriptionInfo
import raiffeisen.sbp.sdk.model.`in`.SubscriptionPaymentResponse

interface SubscriptionController {
    fun createQr(subscriptionDto: SubscriptionDto): ResponseEntity<SubscriptionInfo>
    fun getQrInfo(subscriptionId: String): ResponseEntity<SubscriptionInfo>
    fun getSubscriptionStatus(subscriptionId: String): ResponseEntity<String>
    fun paySubscription(subscriptionId: String, dto: SubscriptionPaymentDto): ResponseEntity<SubscriptionPaymentResponse>
    fun getSubscriptionPaymentInfo(subscriptionId: String, order: String): ResponseEntity<SubscriptionPaymentResponse>
}
