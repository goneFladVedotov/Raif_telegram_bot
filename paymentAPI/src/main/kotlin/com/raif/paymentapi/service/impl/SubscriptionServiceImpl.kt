package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.dto.SubscriptionDto
import com.raif.paymentapi.domain.dto.SubscriptionPaymentDto
import com.raif.paymentapi.service.SubscriptionService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.client.SbpClient
import raiffeisen.sbp.sdk.model.`in`.SubscriptionInfo
import raiffeisen.sbp.sdk.model.`in`.SubscriptionPaymentResponse
import raiffeisen.sbp.sdk.model.out.Subscription
import raiffeisen.sbp.sdk.model.out.SubscriptionPayment

@Service
class SubscriptionServiceImpl(
    @Value("\${raif.sbpMerchantId}")
    private val sbpMerchantId: String,
    @Value("\${raif.secretKey}")
    private val secretKey: String
): SubscriptionService {
    override fun createQr(subscriptionDto: SubscriptionDto): SubscriptionInfo {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        val subscription = Subscription();
        subscription.id = subscriptionDto.id
        subscription.subscriptionPurpose = subscriptionDto.subscriptionPurpose
        subscription.sbpMerchantId = sbpMerchantId
        subscription.redirectUrl = subscriptionDto.redirectUrl
        return sbpClient.createSubscriptionQR(subscription)
    }

    override fun getQrInfo(subscriptionId: String): SubscriptionInfo {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        return sbpClient.getSubscriptionInfo(subscriptionId)
    }

    override fun paySubscription(subscriptionId: String, dto: SubscriptionPaymentDto): SubscriptionPaymentResponse {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)

        val subscriptionPayment = SubscriptionPayment();
        subscriptionPayment.additionalInfo = dto.additionalInfo?:""
        subscriptionPayment.amount = dto.amount
        subscriptionPayment.order = dto.order
        subscriptionPayment.currency = dto.currency?:"RUB"

        return sbpClient.paySubscription(subscriptionId, subscriptionPayment)
    }

    override fun getSubscriptionPaymentInfo(subscriptionId: String, order: String): SubscriptionPaymentResponse {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        return sbpClient.getSubscriptionPayment(subscriptionId, order)
    }
}