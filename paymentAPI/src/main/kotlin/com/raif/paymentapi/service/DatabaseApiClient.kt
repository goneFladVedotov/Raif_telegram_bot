package com.raif.paymentapi.service

import com.raif.paymentapi.domain.model.PaymentInformation
import com.raif.paymentapi.domain.model.QrInformation
import com.raif.paymentapi.domain.model.RefundInformation

interface DatabaseApiClient {
    fun save(qrInformation: QrInformation)
    fun save(refundInformation: RefundInformation)
    fun update(url: String, id: String, status: String)
}