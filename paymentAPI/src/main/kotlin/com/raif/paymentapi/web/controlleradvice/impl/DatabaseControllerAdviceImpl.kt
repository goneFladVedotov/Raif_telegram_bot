package com.raif.paymentapi.web.controlleradvice.impl

import com.raif.paymentapi.exception.DatabaseRequestException
import com.raif.paymentapi.web.controlleradvice.DatabaseControllerAdvice
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.RestClientException

@RestControllerAdvice
class DatabaseControllerAdviceImpl: DatabaseControllerAdvice {
    @ExceptionHandler(DatabaseRequestException::class)
    override fun handleDatabaseRequest(e: DatabaseRequestException): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
    }

    @ExceptionHandler(RestClientException::class)
    override fun handleRestClient(e: RestClientException): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(e.message)
    }
}