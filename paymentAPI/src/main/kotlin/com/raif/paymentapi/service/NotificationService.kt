package com.raif.paymentapi.service

import com.raif.paymentapi.domain.dto.NotificationDto

interface NotificationService {
    fun configureCallback()
    fun handleCallback(notificationString: String)
}