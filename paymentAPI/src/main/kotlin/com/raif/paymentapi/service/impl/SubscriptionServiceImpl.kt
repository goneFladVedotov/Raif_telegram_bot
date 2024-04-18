package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.dto.SubscriptionDto
import com.raif.paymentapi.domain.dto.SubscriptionPaymentDto
import com.raif.paymentapi.domain.model.PaymentInformation
import com.raif.paymentapi.domain.model.SubscriptionInformation
import com.raif.paymentapi.service.DatabaseApiClient
import com.raif.paymentapi.service.SubscriptionService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.client.SbpClient
import raiffeisen.sbp.sdk.model.`in`.SubscriptionInfo
import raiffeisen.sbp.sdk.model.`in`.SubscriptionPaymentResponse
import raiffeisen.sbp.sdk.model.out.Subscription
import raiffeisen.sbp.sdk.model.out.SubscriptionPayment
import java.time.ZonedDateTime

@Service
class SubscriptionServiceImpl(
    private val databaseApiClient: DatabaseApiClient,
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
        val subscriptionInfo = sbpClient.createSubscriptionQR(subscription)
        databaseApiClient.save(
            SubscriptionInformation(
                subscriptionInfo.id,
                subscriptionInfo.createDate,
                subscriptionInfo.status.toString(),
                subscriptionInfo.qr.id,
                subscriptionInfo.qr.payload,
                subscriptionInfo.qr.url
            )
        )
        return subscriptionInfo
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

        val result = sbpClient.paySubscription(subscriptionId, subscriptionPayment)
        databaseApiClient.save(
            PaymentInformation(
                result.additionalInfo,
                result.amount,
                ZonedDateTime.now(),
                result.currency,
                result.order,
                result.paymentStatus,
                result.qrId,
                sbpMerchantId,
                ZonedDateTime.now(),
                0L
            )
        )
        return result
    }

    override fun getSubscriptionPaymentInfo(subscriptionId: String, order: String): SubscriptionPaymentResponse {
        val sbpClient = SbpClient(SbpClient.TEST_URL, sbpMerchantId, secretKey)
        return sbpClient.getSubscriptionPayment(subscriptionId, order)
    }
}