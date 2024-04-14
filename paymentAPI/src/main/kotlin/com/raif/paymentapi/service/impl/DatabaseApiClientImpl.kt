package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.model.*
import com.raif.paymentapi.exception.DatabaseRequestException
import com.raif.paymentapi.service.DatabaseApiClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class DatabaseApiClientImpl(
    @Value("\${http.address.database}")
    private val address: String
) : DatabaseApiClient {
    private val restTemplate: RestTemplate = RestTemplate()
    private val saveQrLock: Any = Any()
    private val saveRefundLock: Any = Any()
    private val savePaymentLock: Any = Any()
    private val saveSubscriptionLock: Any = Any()
    private val saveOrderLock = Any()

    override fun save(qrInformation: QrInformation) {
        synchronized(saveQrLock) {
            val response = restTemplate.postForEntity(
                "$address/database-api/v1/qrs", qrInformation, Any::class.java
            )
            if (response.statusCode != HttpStatusCode.valueOf(200)) {
                throw DatabaseRequestException("bad status code: ${response.statusCode.value()}")
            }
        }
    }

    override fun save(refundInformation: RefundInformation) {
        synchronized(saveRefundLock) {
            val response = restTemplate.postForEntity(
                "$address/database-api/v1/refund",
                refundInformation,
                Any::class.java
            )
            if (response.statusCode != HttpStatusCode.valueOf(200)) {
                throw DatabaseRequestException("bad status code: ${response.statusCode.value()}")
            }
        }
    }

    override fun save(paymentInformation: PaymentInformation) {
        synchronized(savePaymentLock) {
            val response = restTemplate.postForEntity(
                "$address/database-api/v1/payments",
                paymentInformation,
                Any::class.java
            )
            if (response.statusCode != HttpStatusCode.valueOf(200)) {
                throw DatabaseRequestException("bad status code: ${response.statusCode.value()}")
            }
        }
    }

    override fun save(subscriptionInformation: SubscriptionInformation) {
        synchronized(saveSubscriptionLock) {
            val response = restTemplate.postForEntity(
                "$address/database-api/v1/subscription",
                subscriptionInformation,
                Any::class.java
            )
            if (response.statusCode != HttpStatusCode.valueOf(200)) {
                throw DatabaseRequestException("bad request: ${response.statusCode.value()}")
            }
        }
    }

    override fun save(orderInformation: OrderInformation) {
        synchronized(saveOrderLock) {
            val response = restTemplate.postForEntity(
                "$address/database-api/v1/order",
                orderInformation,
                Any::class.java
            )
            if (response.statusCode != HttpStatusCode.valueOf(200)) {
                throw DatabaseRequestException("bad request: ${response.statusCode.value()}")
            }
        }
    }

    override fun update(url: String, id: String, status: String) {
        restTemplate.put(
            address + url + id,
            status
        )
    }
}