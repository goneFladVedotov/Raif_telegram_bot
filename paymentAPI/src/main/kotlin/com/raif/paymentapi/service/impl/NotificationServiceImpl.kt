package com.raif.paymentapi.service.impl

import com.raif.paymentapi.service.NotificationService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import raiffeisen.sbp.sdk.model.PaymentNotification
import raiffeisen.sbp.sdk.util.SbpUtil

@Service
class NotificationServiceImpl(
    @Value("\${notification.callback.value}")
    private val callbackUrl: String,
    @Value("\${notification.url.value}")
    private val url: String
): NotificationService {
    override fun configureCallback() {
        val restTemplate = RestTemplate();
        val response = restTemplate.postForLocation(callbackUrl, url)
    }
}