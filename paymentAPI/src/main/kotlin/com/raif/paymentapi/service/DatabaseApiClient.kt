package com.raif.paymentapi.service

import com.raif.paymentapi.domain.model.*

interface DatabaseApiClient {
    fun save(qrInformation: QrInformation)
    fun save(refundInformation: RefundInformation)
    fun save(paymentInformation: PaymentInformation)
    fun save(subscriptionInformation: SubscriptionInformation)
    fun save(orderInformation: OrderInformation)
    fun update(url: String, id: String, status: String)
}