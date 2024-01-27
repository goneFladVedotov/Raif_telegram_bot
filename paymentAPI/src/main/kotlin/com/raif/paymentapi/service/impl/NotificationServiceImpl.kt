package com.raif.paymentapi.service.impl

import com.raif.paymentapi.service.DatabaseApiClient
import com.raif.paymentapi.service.NotificationService
import com.raif.paymentapi.service.QrService
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
    private val url: String,
    private val databaseApiClient: DatabaseApiClient,
    private val qrService: QrService
) : NotificationService {
    override fun configureCallback() {
        val restTemplate = RestTemplate();
        val response = restTemplate.postForLocation(callbackUrl, url)
    }

    override fun handleCallback(notificationString: String) {
        val paymentNotification: PaymentNotification = SbpUtil.parseNotification(notificationString)
        databaseApiClient.update(
            "http://147.78.66.234:9091/database-api/v1/payments/",
            paymentNotification.qrId,
            paymentNotification.paymentStatus
        )
        val qrStatus = qrService.getQrInfo(paymentNotification.qrId).qrStatus
        databaseApiClient.update("http://147.78.66.234:9091/database-api/v1/qrs/",
            paymentNotification.qrId,
            qrStatus)
    }
}