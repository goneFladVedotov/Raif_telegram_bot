package com.raif.paymentapi.service.impl

import com.raif.paymentapi.service.DatabaseApiClient
import com.raif.paymentapi.service.NotificationService
import com.raif.paymentapi.service.QrService
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.model.PaymentNotification
import raiffeisen.sbp.sdk.util.SbpUtil

@Service
class NotificationServiceImpl(
    private val databaseApiClient: DatabaseApiClient,
    private val qrService: QrService
) : NotificationService {
    override fun handleCallback(notificationString: String) {
        val paymentNotification: PaymentNotification = SbpUtil.parseNotification(notificationString)
        databaseApiClient.updateStatus(
            "/database-api/v1/payments/",
            paymentNotification.order,
            paymentNotification.paymentStatus
        )
        val qrStatus = qrService.getQrInfo(paymentNotification.qrId).qrStatus
        databaseApiClient.updateStatus(
            "/database-api/v1/qrs/",
            paymentNotification.qrId,
            qrStatus
        )
    }
}