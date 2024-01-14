package com.raif.databaseapi.service

import com.raif.databaseapi.domain.PaymentInfo

interface PaymentService {
    fun savePaymentInfo(paymentInfo: PaymentInfo)
    fun updatePaymentInfo(qrId: String, paymentStatus: String)
    fun getPaymentInfo(qrId: String): PaymentInfo
    fun getAll(): List<PaymentInfo>
}