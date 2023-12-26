package com.raif.paymentapi.web.controlleradvice.impl

import com.raif.paymentapi.web.controlleradvice.SbpClientControllerAdvice
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import raiffeisen.sbp.sdk.exception.ContractViolationException
import raiffeisen.sbp.sdk.exception.SbpException
import java.io.IOException

@RestControllerAdvice
class SbpClientControllerAdviceImpl: SbpClientControllerAdvice {
    @ExceptionHandler(IOException::class)
    override fun handleIO(e: IOException): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(e.message)
    }

    @ExceptionHandler(SbpException::class)
    override fun handleSbp(e: SbpException): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(e.message)
    }

    @ExceptionHandler(ContractViolationException::class)
    override fun handleContractViolation(e: ContractViolationException): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(e.message)
    }

    @ExceptionHandler(Exception::class)
    override fun handle(e: Exception): ResponseEntity<*> {
        e.printStackTrace()
        return ResponseEntity.internalServerError().body(e.message)
    }
}