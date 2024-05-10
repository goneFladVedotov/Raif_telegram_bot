package com.raif.databaseapi.web.controller

import com.raif.databaseapi.web.dto.PaymentInfoDto
import org.springframework.http.ResponseEntity

interface PaymentController {
    fun savePaymentInfo(paymentInfoDto: PaymentInfoDto): ResponseEntity<*>
    fun updatePaymentInfo(qrId: String, paymentStatus: String): ResponseEntity<*>
    fun getPaymentInfo(qrId: String): ResponseEntity<PaymentInfoDto>
    fun getAll(): ResponseEntity<List<PaymentInfoDto>>
}