package com.raif.paymentapi.web.controller

interface CallbackController {
    fun handleCallback(notificationString: String)
    fun configureCallback()
}