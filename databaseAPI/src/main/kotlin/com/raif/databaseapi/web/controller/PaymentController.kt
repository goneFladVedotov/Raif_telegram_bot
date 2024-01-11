package com.raif.databaseapi.web.controller

import com.raif.databaseapi.domain.PaymentInfo
import org.springframework.http.ResponseEntity

interface PaymentController {
    fun savePaymentInfo(paymentInfo: PaymentInfo): ResponseEntity<*>
    fun updatePaymentInfo(qrId: String, paymentStatus: String): ResponseEntity<*>
    fun getPaymentInfo(qrId: String): ResponseEntity<*>
}