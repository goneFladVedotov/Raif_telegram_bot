package com.raif.paymentapi.service

import com.raif.paymentapi.domain.model.PaymentInformation
import com.raif.paymentapi.domain.model.QrInformation
import com.raif.paymentapi.domain.model.RefundInformation
import com.raif.paymentapi.domain.model.SubscriptionInformation

interface DatabaseApiClient {
    fun save(qrInformation: QrInformation)
    fun save(refundInformation: RefundInformation)
    fun save(paymentInformation: PaymentInformation)
    fun save(subscriptionInformation: SubscriptionInformation)
    fun update(url: String, id: String, status: String)
}