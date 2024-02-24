package com.raif.paymentapi.web.controller.impl

import com.raif.paymentapi.service.NotificationService
import com.raif.paymentapi.web.controller.CallbackController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/payment-api/v1/callback")
class CallbackControllerImpl(
    private val notificationService: NotificationService
): CallbackController {
    @PostMapping
    override fun handleCallback(@RequestBody notificationString: String) {
        notificationService.handleCallback(notificationString)
    }

    @PostMapping("/config")
    override fun configureCallback() {
        notificationService.configureCallback()
    }


}