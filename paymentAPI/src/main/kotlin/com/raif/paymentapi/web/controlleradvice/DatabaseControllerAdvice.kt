package com.raif.paymentapi.web.controlleradvice

import com.raif.paymentapi.exception.DatabaseRequestException
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestClientException


interface DatabaseControllerAdvice {
    fun handleDatabaseRequest(e: DatabaseRequestException): ResponseEntity<*>
    fun handleRestClient(e: RestClientException): ResponseEntity<*>
}