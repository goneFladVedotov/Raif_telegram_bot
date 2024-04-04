package com.raif.paymentapi.service

import com.raif.paymentapi.domain.dto.SubscriptionDto
import com.raif.paymentapi.domain.dto.SubscriptionPaymentDto
import raiffeisen.sbp.sdk.model.`in`.SubscriptionInfo
import raiffeisen.sbp.sdk.model.`in`.SubscriptionPaymentResponse

interface SubscriptionService {
    fun createQr(subscriptionDto: SubscriptionDto):SubscriptionInfo
    fun getQrInfo(subscriptionId: String): SubscriptionInfo
    fun paySubscription(subscriptionId: String, dto: SubscriptionPaymentDto): SubscriptionPaymentResponse
    fun getSubscriptionPaymentInfo(subscriptionId: String, order: String): SubscriptionPaymentResponse
}