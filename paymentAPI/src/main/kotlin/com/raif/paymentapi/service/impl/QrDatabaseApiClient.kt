package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.model.QrInformation
import com.raif.paymentapi.exception.DatabaseRequestException
import com.raif.paymentapi.service.DatabaseApiClient
import org.springframework.http.HttpStatusCode
import org.springframework.web.client.RestTemplate


class QrDatabaseApiClient : DatabaseApiClient {
    private val restTemplate: RestTemplate = RestTemplate()
    override fun save(qrInformation: QrInformation) {
        val response = restTemplate.postForEntity(
            "http://localhost:9091/database-api/v1/qrs", qrInformation, Any::class.java
        )
        if (response.statusCode != HttpStatusCode.valueOf(200)) {
            throw DatabaseRequestException("bad status code: ${response.statusCode.value()}")
        }
    }

    override fun update(qrInformation: QrInformation) {
        restTemplate.put(
            "http://localhost:9091/database-api/v1/qrs",
            qrInformation
        )
    }
}