package com.raif.paymentapi.service

import com.raif.paymentapi.domain.model.PaymentInformation
import com.raif.paymentapi.domain.model.QrInformation

interface DatabaseApiClient {
    fun save(qrInformation: QrInformation)
    fun update(qrInformation: QrInformation)
    fun update(paymentInformation: PaymentInformation)
}