package com.raif.paymentapi.service

interface NotificationService {
    fun handleCallback(notificationString: String)
}